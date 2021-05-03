package com.example.myfirstapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddGoalsActivity : AppCompatActivity() { // TimePickerDialog.OnTimeSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goals)

        val spinner: Spinner = findViewById(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.add_goals,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        val c = findViewById<CalendarView>(R.id.calendarView)
        c.setMinDate(System.currentTimeMillis()) // Make sure user can't set goal date to previous date

        // get a calendar instance
        val calendar = Calendar.getInstance()

        // calendar view date change listener
        c.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // set the calendar date as calendar view selected date
            calendar.set(year,month,dayOfMonth)

            // set this date as calendar view selected date
            c.date = calendar.timeInMillis

            // format the calendar view selected date
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            //textView.text = "Date changed\n"
            //textView.append(dateFormatter.format(calendar.time))
            //textView.setTextColor(Color.BLUE)
        }
    }

    fun setGoal(view: View) {
        val tex = findViewById<TextView>(R.id.textView6)
        tex.setVisibility(View.VISIBLE)

        val tv9 = findViewById<TextView>(com.example.myfirstapp.R.id.textView9)
        val etn = findViewById<EditText>(com.example.myfirstapp.R.id.editTextNumber)
        val c = findViewById<CalendarView>(com.example.myfirstapp.R.id.calendarView)
        if (tv9.text.toString() == "textView9") {
            if (etn.text.toString() == "") {
                tex.setText("Goal not added. Please set a target and a time.")
            }
            else {
                tex.setText("Goal not added. Please set a time.")
            }
        }
        else if (etn.text.toString() == "") {
            tex.setText("Goal not added. Please set a target.")
        }
        else {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val selectedDate: String = sdf.format(Date(c.getDate()))
            val outputText = "Goal added for " + selectedDate + " at " + tv9.text.toString()
            var hours = tv9.text.toString()
            var minutes = ""
            var isAm = false

            if (hours.substring(1, 2).equals(":")) {
                if (hours.substring(5, 6).equals("a")) {
                    isAm = true
                }
                minutes = hours.substring(2, 4)
                hours = hours.substring(0, 1)
            }
            else {
                if (hours.substring(6, 7).equals("a")) {
                    isAm = true
                }
                minutes = hours.substring(3, 5)
                hours = hours.substring(0, 2)
            }
            val spinner: Spinner = findViewById(R.id.spinner)

            val newGoal = Goal(spinner.getSelectedItemPosition(), etn.text.toString().toInt(), c.getDate(), hours.toInt(), minutes.toInt(), isAm, false)

            var goals: ArrayList<Goal>

            val gson = Gson()

            // Check if GOALS_FILE exists
            val file =  File(getApplicationContext().filesDir, GOALS_FILE);
            file.readLines()
            if(file.exists()) {
                // Exists
                val inputStream: InputStream = File(getApplicationContext().filesDir, GOALS_FILE).inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
                val myType = object : TypeToken<List<Goal>>() {}.type
                goals = gson.fromJson<ArrayList<Goal>>(inputString, myType)
            }
            else {
                // Does not exist
                goals = ArrayList<Goal>()
            }

            goals.add(newGoal)

            //gson.toJson(newGoal, FileWriter(GOALS_FILE))

            // Convert from Goals array to json
            val jsonOutput = gson.toJson(goals)

            val filename = GOALS_FILE
            //val fileContents = "Hello world!"
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(jsonOutput.toByteArray())
            }
            //

            //tex.setText(jsonOutput)
            //tex.setText("Goal added")
            tex.setText(outputText)
        }
    }

    fun showTimePickerDialog(v: View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    fun setDate(v: View) {
        //val c = findViewById<CalendarView>(com.example.myfirstapp.R.id.calendarView)
        //c.setDate
    }

}