package com.example.myfirstapp

import android.R
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val tv9 = activity?.findViewById<TextView>(com.example.myfirstapp.R.id.textView9)
        if (tv9 != null) {
            var hourOfDayString = ""
            var ampm = "a.m."
            var minuteAppend = ""
            if (hourOfDay > 12) {
                hourOfDayString = "" + (hourOfDay - 12)
                ampm = "p.m."
            }
            else if (hourOfDay == 0) {
                hourOfDayString = "12"
            }
            else {
                hourOfDayString = "" + hourOfDay
            }
            if (minute < 10)
                minuteAppend = "0"
            var outputText = hourOfDayString + ":" + minuteAppend + minute + " " + ampm
            tv9.setText(outputText)
            tv9.setVisibility(View.VISIBLE)
        }
    }
}
