package com.example.baqiala

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqiala.data.Note
import com.example.baqiala.databinding.ActivityAddNoteBinding
import com.example.baqiala.databinding.BottomSheetLayoutBinding
import com.example.baqiala.viewModel.MyViewModel
import java.util.Calendar

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
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
    private var nameNotEmpty: Boolean = false
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        binding.dismissBtn.setOnClickListener {
            finish()
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

        binding.date.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isAddDate = !p0.isNullOrBlank()
                addBtn()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.time.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isTimeAdd = !p0.isNullOrBlank()
                addBtn()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.baqiAlaName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameNotEmpty = !p0.isNullOrBlank()
                name=p0.toString()
                addBtn()

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.addBtn.setOnClickListener {
            if (isAddDate && isTimeAdd && nameNotEmpty)
                addNote(name, mDay,mMonth,mYear,mHour,mMinute)
        }

    }

    private fun addNote(
        name: String,
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        minute: Int
    ) {
        viewModel.addUser(Note(name=name, day=day, month=month, year=year, hour=hour,minute=minute))

        finish()
    }

    fun addBtn() {
        if (isAddDate && isTimeAdd && nameNotEmpty) {
            binding.addBtn.alpha = 1.0F
        } else {
            binding.addBtn.alpha = 0.2F
        }
    }
}