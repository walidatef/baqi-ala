package com.example.baqiala.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.baqiala.R
import com.example.baqiala.data.Note
import com.example.baqiala.viewModel.MyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.log

class MyAdapter(
    private val myDataset: MutableList<Note>,
    private val context: Context, private val viewModel: MyViewModel
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    companion object{
        lateinit var countDownTimer: CountDownTimer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_note_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = myDataset[position]
        holder.name.text = data.name
        holder.secondsBar.max = 60
        holder.minutesBar.max = 60
        holder.hoursBar.max = 24


        holder.bind(data, context)
    }

    override fun getItemCount() = myDataset.size
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.my_text)

        val secondsTxt: TextView = view.findViewById(R.id.seconds_stay_txt)
        val minutesTxt: TextView = view.findViewById(R.id.minutes_stay_txt)
        val hoursTxt: TextView = view.findViewById(R.id.hours_stay_txt)
        val daysTxt: TextView = view.findViewById(R.id.days_stay_txt)

        private val startTxt: TextView = view.findViewById(R.id.start_txt)
        private val endTxt: TextView = view.findViewById(R.id.end_txt)

        val seekBarFromTo: ProgressBar = view.findViewById(R.id.progressBar_start_end)


        val secondsBar: ProgressBar = view.findViewById(R.id.seconds_stay_bar)
        val minutesBar: ProgressBar = view.findViewById(R.id.minutes_stay_bar)
        val hoursBar: ProgressBar = view.findViewById(R.id.hours_stay_bar)
        val daysBar: ProgressBar = view.findViewById(R.id.days_stay_bar)

        val hoursLayout: RelativeLayout = view.findViewById(R.id.hours_layout)
        val minutesLayout: RelativeLayout = view.findViewById(R.id.minutes_layout)
        val daysLayout: RelativeLayout = view.findViewById(R.id.days_layout)

        val finished_layout: LinearLayout = view.findViewById(R.id.finished_layout)
        val all_progress_layout: LinearLayout = view.findViewById(R.id.all_progress_layout)



        var fromTo: Long = 0

        companion object {

            var maxBar: Long = 0
        }


        fun bind(item: Note, context: Context) {
            getEndTime(item)
            val maxTime = GetDate(maxBar)
            daysBar.max = maxTime.days.toInt()
            seekBarFromTo.max = maxBar.toInt()



            countDownTimer = object : CountDownTimer(getEndTime(item), 1000) {


                override fun onTick(millisUntilFinished: Long) {
                    val converted = GetDate(millisUntilFinished)

                    all_progress_layout.visibility = View.VISIBLE
                    finished_layout.visibility = View.INVISIBLE
                    // Seconds
                    secondsTxt.text = converted.seconds.toString()
                    secondsBar.progress = converted.seconds.toInt()
                    // Minutes
                    if (converted.minutes == 0L) {
                        minutesLayout.visibility = View.GONE
                    } else {
                        minutesLayout.visibility = View.VISIBLE
                        minutesTxt.text = converted.minutes.toString()
                        minutesBar.progress = converted.minutes.toInt()
                    }
                    // Hours
                    if (converted.hours == 0L) {
                        hoursLayout.visibility = View.GONE
                    } else {
                        hoursLayout.visibility = View.VISIBLE
                        hoursTxt.text = converted.hours.toString()
                        hoursBar.progress = converted.hours.toInt()
                    }
                    // days
                    if (converted.days <= 0L) {
                        daysLayout.visibility = View.GONE
                    } else {
                        daysLayout.visibility = View.VISIBLE
                        daysTxt.text = converted.days.toString()
                        daysBar.progress = converted.days.toInt()
                    }

                    fromTo += 1000
                    seekBarFromTo.progress = (fromTo).toInt()

                }

                override fun onFinish() {
                    hideAllProgressBar()
                    showFinishedLayout()
                    endTxt.setTextColor(ContextCompat.getColor(context, R.color.black))
                    seekBarFromTo.max = 100
                    seekBarFromTo.progress = 100

                }
            }.start()

        }


        fun stopTimer() {
            countDownTimer.cancel()
        }


        fun showFinishedLayout() {
            finished_layout.visibility = View.VISIBLE
        }

        fun hideAllProgressBar() {
            all_progress_layout.visibility = View.GONE

        }

        @SuppressLint("SetTextI18n")
        private fun getEndTime(note: Note): Long {

            val time = Calendar.getInstance().time
            val year = SimpleDateFormat("yyyy")
            val month = SimpleDateFormat("MM")
            val day = SimpleDateFormat("dd")
            val hour = SimpleDateFormat("HH")
            val minute = SimpleDateFormat("mm")
            val second = SimpleDateFormat("ss")

            val mYear = year.format(time)
            val mMonth = month.format(time)
            val mDay = day.format(time)
            val mHour = hour.format(time)
            val mMinute = minute.format(time)
            val mSecond = second.format(time)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val withoutSecond = SimpleDateFormat("yyyy-MM-dd ")

            val startDate = dateFormat.parse("$mYear-$mMonth-$mDay $mHour:$mMinute:$mSecond")
            val endDate =
                dateFormat.parse(note.year.toString() + "-" + note.month.toString() + "-" + note.day.toString() + " " + note.hour.toString() + ":" + note.minute.toString() + ":" + note.second)

            //add time
            val date = Date(note.addTime)

            if (year.format(date).toInt() != note.year) {
                val addTime = withoutSecond.format(date)
                startTxt.text = addTime + amPm24To12(
                    hour.format(date).toInt(), minute.format(date).toInt()
                )
                // end time
                if (endDate != null) {
                    endTxt.text =
                        note.year.toString() + "-" + note.month.toString() + "-" + note.day.toString() + " " + amPm24To12(
                            note.hour,
                            note.minute
                        )
                }
            } else if (month.format(date).toInt() == note.month && day.format(date)
                    .toInt() == note.day
            ) {

                startTxt.text = amPm24To12(hour.format(date).toInt(), minute.format(date).toInt())
                // end time
                if (endDate != null) {
                    endTxt.text = amPm24To12(note.hour, note.minute)
                }
            } else {
                startTxt.text =
                    month.format(date) + "/" + day.format(date) + " " + amPm24To12(
                        hour.format(date).toInt(), minute.format(date).toInt()
                    )
                // end time
                if (endDate != null) {
                    endTxt.text =
                        note.month.toString() + "/" + note.day.toString() + " " + amPm24To12(
                            hour.format(date).toInt(), minute.format(date).toInt()
                        )
                }
            }

            val calendarStartFromIt = Calendar.getInstance()
            if (startDate != null) {
                calendarStartFromIt.time = startDate
            }

            val calendarEnd = Calendar.getInstance()
            if (endDate != null) {
                calendarEnd.time = endDate
            }

            maxBar = (calendarEnd.timeInMillis - note.addTime)
            fromTo = (calendarStartFromIt.timeInMillis - note.addTime)
            val between = (calendarEnd.timeInMillis - calendarStartFromIt.timeInMillis)

            clearTime(calendarStartFromIt)
            clearTime(calendarEnd)

            return between
        }

        private fun clearTime(calendar: Calendar) {
            calendar.set(Calendar.YEAR, 0)
            calendar.set(Calendar.MONTH, 0)
            calendar.set(Calendar.DAY_OF_MONTH, 0)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

        class GetDate(millis: Long) {
            val seconds = (millis / 1000) % 60
            val minutes = (millis / (1000 * 60)) % 60
            val hours = (millis / (1000 * 60 * 60)) % 24
            val days = (millis / (1000 * 60 * 60 * 24))
        }

        private fun amPm24To12(hour: Int, minute: Int): String {
            val amPm = if (hour < 12) "AM" else "PM"
            val mHour = if (hour % 12 == 0) 12 else hour % 12
            return "$mHour:$minute $amPm"
        }
    }

    override fun onViewRecycled(holder: MyViewHolder) {
        holder.stopTimer()
        super.onViewRecycled(holder)
    }


    fun removeItem(position: Int) {
        countDownTimer.cancel()
        val noteWillDeleted = myDataset[position]
        viewModel.deleteNote(context, noteWillDeleted)
        myDataset.removeAt(position)
        notifyItemRemoved(position)
    }


}
