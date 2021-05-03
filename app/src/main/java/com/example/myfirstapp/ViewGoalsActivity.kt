package com.example.myfirstapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ViewGoalsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_goals)

        val spinner: Spinner = findViewById(R.id.spinner2)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.add_goals,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            //.adapter = adapter
        }

        //val tv12 = findViewById<TextView>(com.example.myfirstapp.R.id.textView12)

        val inputStream: InputStream =
            File(getApplicationContext().filesDir, "goals.json").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        //tv12.setText(inputString)

        var goals: ArrayList<Goal>
        var goalsToDisplay: ArrayList<Goal>

        val gson = Gson()

        // Check if goals.json exists
        val file = File(getApplicationContext().filesDir, "goals.json");
        //file.readLines()
        if (file.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream =
                File(getApplicationContext().filesDir, "goals.json").inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val myType = object : TypeToken<List<Goal>>() {}.type
            goals = gson.fromJson<ArrayList<Goal>>(
                inputString,
                myType
            ) // convert from json to Goals array
        } else {
            // Does not exist
            goals = ArrayList<Goal>()
        }

        if (goals.size > 0) {
            val c = findViewById<CalendarView>(R.id.calendarView2)

            // Find minimum date a goal was set so user can't go too far back in calendar
            var earliestDate: Long = 9223372036854775807 // max value that long can store
            var latestDate: Long = 0
            for (i in goals.indices) {
                if (goals.get(i).date < earliestDate) {
                    earliestDate = goals.get(i).date
                }
                if (goals.get(i).date > latestDate) {
                    latestDate = goals.get(i).date
                }
            }
            c.setMinDate(earliestDate)
            c.setMaxDate(latestDate)

            // get a calendar instance
            val calendar = Calendar.getInstance()


            // set this date as calendar view selected date
            c.date = calendar.timeInMillis

            // format the calendar view selected date
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            //textView.text = "Date changed\n"
            //textView.append(dateFormatter.format(calendar.time))
            //textView.setTextColor(Color.BLUE)

            // Display all goals that match date selected
            goalsToDisplay = ArrayList<Goal>()
            var goalsInSpinner: ArrayList<String> = ArrayList<String>()
            for (i in goals.indices) {

                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val selectedDate: String = sdf.format(Date(c.getDate()))
                val arrayDate: String = sdf.format(Date(goals.get(i).date))

                if (selectedDate.equals(arrayDate)) {
                    goalsToDisplay.add(goals.get(i))
                    var spinnerString: String = ""
                    if (goals.get(i).goalMet) {
                        spinnerString = "[COMPLETED] "
                    }
                    spinnerString += "" + goals.get(i).hours + ":"
                    if (goals.get(i).minutes < 10) {
                        spinnerString += "0"
                    }
                    spinnerString += "" + goals.get(i).minutes + " "
                    if (goals.get(i).isAm) {
                        spinnerString += "a.m."
                    } else {
                        spinnerString += "p.m."
                    }
                    spinnerString += " "
                    if (goals.get(i).goalId == 0) {
                        spinnerString += "Weigh at most " + goals.get(i).target + " lbs (Smart Scale)"
                    } else if (goals.get(i).goalId == 1) {
                        spinnerString += "Walk at least " + goals.get(i).target + " steps (IMU)"
                    } else if (goals.get(i).goalId == 2) {
                        spinnerString += "Walk for at least " + goals.get(i).target + " minutes (IMU)"
                    } else if (goals.get(i).goalId == 3) {
                        spinnerString += "Do at least " + goals.get(i).target + " reps (KINECT)"
                    } else if (goals.get(i).goalId == 4) {
                        spinnerString += "Do at least " + goals.get(i).target + " jumping jacks (KINECT)"
                    }
                    goalsInSpinner.add(spinnerString)
                }
            }

            val jsonOutput = gson.toJson(goalsToDisplay)
            //tv12.setText(jsonOutput)

            // Creating adapter for spinner
            // Creating adapter for spinner
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, goalsInSpinner)

            // Drop down layout style - list view with radio button

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // attaching data adapter to spinner

            // attaching data adapter to spinner
            spinner.adapter = dataAdapter
            //val dateString = "" + c.getDate()
            // tv12.setText(dateString)


            // calendar view date change listener
            c.setOnDateChangeListener { view, year, month, dayOfMonth ->
                // set the calendar date as calendar view selected date
                calendar.set(year, month, dayOfMonth)

                // set this date as calendar view selected date
                c.date = calendar.timeInMillis

                // format the calendar view selected date
                val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
                //textView.text = "Date changed\n"
                //textView.append(dateFormatter.format(calendar.time))
                //textView.setTextColor(Color.BLUE)

                // Display all goals that match date selected
                goalsToDisplay = ArrayList<Goal>()
                var goalsInSpinner: ArrayList<String> = ArrayList<String>()
                for (i in goals.indices) {

                    val sdf = SimpleDateFormat("MM/dd/yyyy")
                    val selectedDate: String = sdf.format(Date(c.getDate()))
                    val arrayDate: String = sdf.format(Date(goals.get(i).date))

                    if (selectedDate.equals(arrayDate)) {
                        goalsToDisplay.add(goals.get(i))
                        var spinnerString: String = ""
                        if (goals.get(i).goalMet) {
                            spinnerString = "[COMPLETED] "
                        }
                        spinnerString += "" + goals.get(i).hours + ":"
                        if (goals.get(i).minutes < 10) {
                            spinnerString += "0"
                        }
                        spinnerString += "" + goals.get(i).minutes + " "
                        if (goals.get(i).isAm) {
                            spinnerString += "a.m."
                        } else {
                            spinnerString += "p.m."
                        }
                        spinnerString += " "
                        if (goals.get(i).goalId == 0) {
                            spinnerString += "Weigh at most " + goals.get(i).target + " lbs (Smart Scale)"
                        } else if (goals.get(i).goalId == 1) {
                            spinnerString += "Walk at least " + goals.get(i).target + " steps (IMU)"
                        } else if (goals.get(i).goalId == 2) {
                            spinnerString += "Walk for at least " + goals.get(i).target + " minutes (IMU)"
                        } else if (goals.get(i).goalId == 3) {
                            spinnerString += "Do at least " + goals.get(i).target + " reps (KINECT)"
                        } else if (goals.get(i).goalId == 4) {
                            spinnerString += "Do at least " + goals.get(i).target + " jumping jacks (KINECT)"
                        }
                        goalsInSpinner.add(spinnerString)
                    }
                }
                val jsonOutput = gson.toJson(goalsToDisplay)
                //tv12.setText(jsonOutput)

                // Creating adapter for spinner
                // Creating adapter for spinner
                val dataAdapter: ArrayAdapter<String> =
                    ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, goalsInSpinner)

                // Drop down layout style - list view with radio button

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // attaching data adapter to spinner
                spinner.adapter = dataAdapter
                //val dateString = "" + c.getDate()
                // tv12.setText(dateString)
            }
        }
    }

    fun deleteGoal(v: View) {
        val c = findViewById<CalendarView>(com.example.myfirstapp.R.id.calendarView2)
        val spinner: Spinner = findViewById(R.id.spinner2)
        var goals: ArrayList<Goal>
        val gson = Gson()

        // Check if goals.json exists
        val file =  File(getApplicationContext().filesDir, "goals.json");
        if(file.exists()) {
                // Exists, get json data from it
                val inputStream: InputStream = File(getApplicationContext().filesDir, "goals.json").inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
                val myType = object : TypeToken<List<Goal>>() {}.type
                goals = gson.fromJson<ArrayList<Goal>>(inputString, myType) // convert from json to Goals array
        }
        else {
            // Does not exist
        goals = ArrayList<Goal>()
        }

        var goalIndices = ArrayList<Int>()
        var goalsToDisplay = ArrayList<Goal>()
        var goalsInSpinner: ArrayList<String> = ArrayList<String>()
        for (i in goals.indices) {

            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val selectedDate: String = sdf.format(Date(c.getDate()))
            val arrayDate: String = sdf.format(Date(goals.get(i).date))

            if (selectedDate.equals(arrayDate)) {
                //goalsToDisplay.add(goals.get(i))
                goalIndices.add(i)
                /*
                var spinnerString: String = ""
                if (goals.get(i).goalMet) {
                    spinnerString = "[COMPLETED] "
                }
                spinnerString += "" + goals.get(i).hours + ":"
                if (goals.get(i).minutes < 10) {
                    spinnerString += "0"
                }
                spinnerString += "" + goals.get(i).minutes + " "
                if (goals.get(i).isAm) {
                    spinnerString += "a.m."
                } else {
                    spinnerString += "p.m."
                }
                spinnerString += " "
                if (goals.get(i).goalId == 0) {
                    spinnerString += "Weigh at most " + goals.get(i).target + " lbs (Smart Scale)"
                } else if (goals.get(i).goalId == 1) {
                    spinnerString += "Walk at least " + goals.get(i).target + " steps (IMU)"
                } else if (goals.get(i).goalId == 2) {
                    spinnerString += "Walk for at least " + goals.get(i).target + " minutes (IMU)"
                } else if (goals.get(i).goalId == 3) {
                    spinnerString += "Do at least " + goals.get(i).target + " reps (KINECT)"
                } else if (goals.get(i).goalId == 4) {
                    spinnerString += "Do at least " + goals.get(i).target + " jumping jacks (KINECT)"
                }
                if (i != goalIndices.get(spinner.getSelectedItemPosition())) {
                    goalsInSpinner.add(spinnerString)
                }
                */

            }
        }

        goals.removeAt(goalIndices.get(spinner.getSelectedItemPosition()))

        val jsonOutput = gson.toJson(goals)


        val filename = "goals.json"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(jsonOutput.toByteArray())
        }

        finish();
        startActivity(getIntent());
    }
}