package com.example.baqiala.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.baqiala.R
import com.example.baqiala.data.Note
import com.example.baqiala.dataBase.MyDatabase
import com.example.baqiala.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.properties.Delegates

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
    private val noteDoa = MyDatabase.getDatabase(this).noteDoa()
    private var startDay by Delegates.notNull<Int>()
    private var startMonth by Delegates.notNull<Int>()
    private var startYear by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.dismissBtn.setOnClickListener {
            finish()
        }

        binding.dateBtn.setOnClickListener {
            val currentDate = Calendar.getInstance()
            startYear = currentDate.get(Calendar.YEAR)
            startMonth = currentDate.get(Calendar.MONTH)
            startDay = currentDate.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    mYear = year
                    mMonth = month + 1
                    mDay = day

                    binding.date.text = "$year - $mMonth - $day"
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
                    val selectedTime = Calendar.getInstance().apply {
                        set(Calendar.YEAR,mYear)
                        set(Calendar.MONTH,mMonth-1)
                        set(Calendar.DAY_OF_MONTH,mDay)
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                    }
                    // Check if the selected time is bigger than the current time
                    if (selectedTime.after(Calendar.getInstance())) {
                        val amPm = if (hourOfDay < 12) "AM" else "PM"
                        var hour = hourOfDay % 12
                        if (hour == 0) {
                            hour = 12
                        }
                        mHour = hourOfDay
                        mMinute = minute
                        this.amPm = amPm
                        binding.time.text = "$hour : $minute $amPm"
                        isTimeAdd = true

                    } else {
                        // Show an error message
                        Snackbar.make(it, getString(R.string.numNotGreater), Snackbar.LENGTH_LONG)
                            .show()

                    }


                }, startHour, startMinute, false
            ).show()

        }

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
                name = p0.toString()
                addBtn()

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.addBtn.setOnClickListener {
            if (isAddDate && isTimeAdd && nameNotEmpty)
                addNote(name, mDay, mMonth, mYear, mHour, mMinute)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addNote(
        name: String,
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        minute: Int
    ) {
        GlobalScope.launch(Dispatchers.IO) {

            noteDoa.insertNote(
                Note(
                    name = name,
                    day = day,
                    month = month,
                    year = year,
                    hour = hour,
                    minute = minute
                )
            )
        }




        finish()
    }

    fun addBtn() {
        if (isAddDate && isTimeAdd && nameNotEmpty) {
            binding.addBtn.alpha = 1.0F
        } else {
            binding.addBtn.alpha = 0.2F
        }
    }

    fun getTime(): Long {

        val time = Calendar.getInstance()
        return time.timeInMillis
    }
}