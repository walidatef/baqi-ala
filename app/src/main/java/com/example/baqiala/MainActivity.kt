package com.example.baqiala

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.baqiala.databinding.ActivityMainBinding
import com.example.baqiala.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.Month
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)


        /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             .setAction("Action", null).show()*/

        _binding.fab.setOnClickListener { view ->

            val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
            val binding = BottomSheetLayoutBinding.inflate(layoutInflater)
            bottomSheetDialog.setContentView(binding.root)
            bottomSheetDialog.show()


            binding.dismissBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            binding.dateBtn.setOnClickListener {
                val currentDate = Calendar.getInstance()
                val startYear = currentDate.get(Calendar.YEAR)
                val startMonth = currentDate.get(Calendar.MONTH)
                val startDay = currentDate.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    this,
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
                    this,
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
                binding.addBtn.setBackgroundColor(getColor(R.color.black))

                binding.addBtn.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    _binding.test.text = "$name    $mYear / $mMonth / $mDay  $mHour  $mMinute $amPm "
                }

            }





        }


    }


}