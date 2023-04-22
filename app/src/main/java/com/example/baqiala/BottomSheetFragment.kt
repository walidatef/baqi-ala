package com.example.baqiala

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.example.baqiala.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


class BottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLayoutBinding
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var mSecond: Int = 0
    private var amPm: String = ""

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    private var isAddDate = false
    private var isTimeAdd = false
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetLayoutBinding.inflate(layoutInflater)
        /*val behavior = BottomSheetBehavior.from(binding.view)

        behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        // expand the BottomSheet
        behavior.state = BottomSheetBehavior.STATE_EXPANDED*/


        binding.dismissBtn.setOnClickListener {
          dismiss()
        }
        binding.dateBtn.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val startYear = currentDate.get(Calendar.YEAR)
            val startMonth = currentDate.get(Calendar.MONTH)
            val startDay = currentDate.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    mYear = year
                    mMonth = month
                    mDay = day

                    binding.date.text = "$year - $month - $day"
                    isAddDate = true
                }, startYear, startMonth, startDay
            ).show()

        }
        binding.timeBtn.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    val amPm = if (hourOfDay < 12) "AM" else "PM"
                    var hour = hourOfDay % 12
                    if (hour == 0) {
                        hour = 12
                    }

                    mHour = hour
                    mMinute = minute
                    this.amPm = amPm


                    binding.time.text = "$hour : $minute $amPm"
                    isTimeAdd = true
                }, startHour, startMinute, false
            ).show()
        }

        //TODO listener for not empty editText
        name = binding.baqiAlaName.text.toString()
        if (isAddDate && isTimeAdd && name.isNotEmpty()) {
            binding.addBtn.setBackgroundColor(getColor(requireContext(),R.color.black))

            binding.addBtn.setOnClickListener {
               dismiss()

            }

        }


        return binding.root
    }




}