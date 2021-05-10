package com.example.myfirstapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ViewEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_events)

        val spinner: Spinner = findViewById(R.id.spinner3)
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

        //val inputStream: InputStream =
        //        File(getApplicationContext().filesDir, EVENTS_FILE).inputStream()
        //val inputString = inputStream.bufferedReader().use { it.readText() }
        //tv12.setText(inputString)

        var events: ArrayList<Goal> = getEvents()
        var eventsToDisplay: ArrayList<Goal>

        val gson = Gson()

        val c = findViewById<CalendarView>(R.id.calendarView3)
        if (events.size > 0) {

            // Find minimum date a goal was set so user can't go too far back in calendar
            var earliestDate: Long = 9223372036854775807 // max value that long can store
            var latestDate: Long = 0
            for (i in events.indices) {
                if (events.get(i).date < earliestDate) {
                    earliestDate = events.get(i).date
                }
                if (events.get(i).date > latestDate) {
                    latestDate = events.get(i).date
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

            // Display all events that match date selected
            eventsToDisplay = ArrayList<Goal>()
            var eventsInSpinner: ArrayList<String> = ArrayList<String>()
            for (i in events.indices) {

                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val selectedDate: String = sdf.format(Date(c.getDate()))
                val arrayDate: String = sdf.format(Date(events.get(i).date))

                if (selectedDate.equals(arrayDate)) {
                    eventsToDisplay.add(events.get(i))
                    var spinnerString: String = ""
                    /*

                    All events get displayed.

                    if (events.get(i).goalMet) {
                        spinnerString = "[COMPLETED] "
                    }
                     */
                    spinnerString += "" + events.get(i).hours + ":"
                    if (events.get(i).minutes < 10) {
                        spinnerString += "0"
                    }
                    spinnerString += "" + events.get(i).minutes + " "
                    if (events.get(i).isAm) {
                        spinnerString += "a.m."
                    } else {
                        spinnerString += "p.m."
                    }
                    spinnerString += " "
                    if (events.get(i).goalId == 0) {
                        spinnerString += "Weighed " + events.get(i).target + " lbs (Smart Scale)"
                    } else if (events.get(i).goalId == 1) {
                        spinnerString += "Walked " + events.get(i).target + " steps (IMU)"
                    } else if (events.get(i).goalId == 2) {
                        spinnerString += "Walk for " + events.get(i).target + " minutes (IMU)"
                    } else if (events.get(i).goalId == 3) {
                        spinnerString += "Did " + events.get(i).target + " reps (KINECT)"
                    } else if (events.get(i).goalId == 4) {
                        spinnerString += "Did " + events.get(i).target + " jumping jacks (KINECT)"
                    } else if (events.get(i).goalId == 5) {
                        spinnerString += "Earned workout grade (0 is highest) of " + events.get(i).target + " (KINECT)"
                    } else if (events.get(i).goalId == 6) {
                        spinnerString += "Workout time was " + events.get(i).target + " minutes (KINECT)"
                    } else if (events.get(i).goalId == 7) {
                        spinnerString += "Heart rate was " + events.get(i).target + " BPM (KINECT)"
                    }
                    eventsInSpinner.add(spinnerString)
                }
            }

            val jsonOutput = gson.toJson(eventsToDisplay)
            //tv12.setText(jsonOutput)

            // Creating adapter for spinner
            // Creating adapter for spinner
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsInSpinner)

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

                // Display all events that match date selected
                eventsToDisplay = ArrayList<Goal>()
                var eventsInSpinner: ArrayList<String> = ArrayList<String>()
                for (i in events.indices) {

                    val sdf = SimpleDateFormat("MM/dd/yyyy")
                    val selectedDate: String = sdf.format(Date(c.getDate()))
                    val arrayDate: String = sdf.format(Date(events.get(i).date))

                    if (selectedDate.equals(arrayDate)) {
                        eventsToDisplay.add(events.get(i))
                        var spinnerString: String = ""
                        /*

                        Display all events
                        if (events.get(i).goalMet) {
                            spinnerString = "[COMPLETED] "
                        }
                         */



                        spinnerString += "" + events.get(i).hours + ":"
                        if (events.get(i).minutes < 10) {
                            spinnerString += "0"
                        }
                        spinnerString += "" + events.get(i).minutes + " "
                        if (events.get(i).isAm) {
                            spinnerString += "a.m."
                        } else {
                            spinnerString += "p.m."
                        }
                        spinnerString += " "
                        if (events.get(i).goalId == 0) {
                            spinnerString += "Weighed " + events.get(i).target + " lbs (Smart Scale)"
                        } else if (events.get(i).goalId == 1) {
                            spinnerString += "Walked " + events.get(i).target + " steps (IMU)"
                        } else if (events.get(i).goalId == 2) {
                            spinnerString += "Walk for " + events.get(i).target + " minutes (IMU)"
                        } else if (events.get(i).goalId == 3) {
                            spinnerString += "Did " + events.get(i).target + " reps (KINECT)"
                        } else if (events.get(i).goalId == 4) {
                            spinnerString += "Did " + events.get(i).target + " jumping jacks (KINECT)"
                        } else if (events.get(i).goalId == 5) {
                            spinnerString += "Earned workout grade (0 is highest) of " + events.get(
                                i
                            ).target + " (KINECT)"
                        } else if (events.get(i).goalId == 6) {
                            spinnerString += "Workout time was " + events.get(i).target + " minutes (KINECT)"
                        } else if (events.get(i).goalId == 7) {
                            spinnerString += "Heart rate was " + events.get(i).target + " BPM (KINECT)"
                        }
                        eventsInSpinner.add(spinnerString)
                    }
                }
                if (eventsInSpinner.size == 0) {
                    spinner.visibility = View.INVISIBLE
                } else {
                    spinner.visibility = View.VISIBLE
                }

                val jsonOutput = gson.toJson(eventsToDisplay)
                //tv12.setText(jsonOutput)

                // Creating adapter for spinner
                // Creating adapter for spinner
                val dataAdapter: ArrayAdapter<String> =
                    ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        eventsInSpinner
                    )

                // Drop down layout style - list view with radio button

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // attaching data adapter to spinner
                spinner.adapter = dataAdapter
                //val dateString = "" + c.getDate()
                // tv12.setText(dateString)
            }
        } else {
            spinner.visibility = View.INVISIBLE
            c.visibility = View.INVISIBLE
            val btn = findViewById<Button>(com.example.myfirstapp.R.id.deleteEvent)
            btn.visibility = View.INVISIBLE
            val tv = findViewById<TextView>(com.example.myfirstapp.R.id.textView10e)
            tv.text = "No Events"
        }
    }

    fun deleteEvent(v: View) {
        val c = findViewById<CalendarView>(com.example.myfirstapp.R.id.calendarView3)
        val spinner: Spinner = findViewById(R.id.spinner3)

        val gson = Gson()

        var events: ArrayList<Goal>
        events = getEvents()

        if (events.size > 0) {

            var eventsIndices = ArrayList<Int>()
            var eventsToDisplay = ArrayList<Goal>()
            var eventsInSpinner: ArrayList<String> = ArrayList<String>()
            for (i in events.indices) {

                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val selectedDate: String = sdf.format(Date(c.getDate()))
                val arrayDate: String = sdf.format(Date(events.get(i).date))

                if (selectedDate.equals(arrayDate)) {
                    //eventsToDisplay.add(events.get(i))
                    eventsIndices.add(i)
                }
            }

            /*
            events.removeAt(eventsIndices.get(spinner.getSelectedItemPosition()))

            val jsonOutput = gson.toJson(events)

            val filename = EVENTS_FILE
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(jsonOutput.toByteArray())
            }
             */
            var deletedEvents: ArrayList<Goal>
            val deletedEventsFile = File(getApplicationContext().filesDir, DELETED_EVENTS_FILE);
            if (deletedEventsFile.exists()) {
                // Exists, get json data from it
                val inputStream: InputStream =
                    File(getApplicationContext().filesDir, DELETED_EVENTS_FILE).inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
                val myType = object : TypeToken<List<Goal>>() {}.type
                deletedEvents = gson.fromJson<ArrayList<Goal>>(
                    inputString,
                    myType
                ) // convert from json to Goals array
            } else {
                // Does not exist
                deletedEvents = ArrayList<Goal>()
            }
            deletedEvents.add(events.get(eventsIndices.get(spinner.getSelectedItemPosition())))

            val jsonOutput = gson.toJson(deletedEvents)

            val filename = DELETED_EVENTS_FILE
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(jsonOutput.toByteArray())

                finish();
                startActivity(getIntent());
            }
        }
    }

    fun getEvents(): ArrayList<Goal> {
        val gson = Gson()
        var events: ArrayList<Goal>
        val file = File(getApplicationContext().filesDir, EVENTS_FILE);
        if (file.exists()) {
            // Exists, get json data from it
                val inputStream: InputStream =
                    File(getApplicationContext().filesDir, EVENTS_FILE).inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
                val myType = object : TypeToken<List<Goal>>() {}.type
                events = gson.fromJson<ArrayList<Goal>>(
                    inputString,
                    myType
                ) // convert from json to Goals array
            } else {
                // Does not exist
                events = ArrayList<Goal>()
            }

            var deletedEvents: ArrayList<Goal>
            val deletedEventsFile = File(getApplicationContext().filesDir, DELETED_EVENTS_FILE);
            if (deletedEventsFile.exists()) {
                // Exists, get json data from it
                val inputStream: InputStream =
                    File(getApplicationContext().filesDir, DELETED_EVENTS_FILE).inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
                val myType = object : TypeToken<List<Goal>>() {}.type
                deletedEvents = gson.fromJson<ArrayList<Goal>>(
                    inputString,
                    myType
                ) // convert from json to Goals array
            } else {
                // Does not exist
                deletedEvents = ArrayList<Goal>()
            }

            var removedAnEvent: Boolean = true
            while (removedAnEvent) {
                removedAnEvent = false
                for (i in events.indices) {
                    for (j in deletedEvents.indices) {
                        if (events.get(i).equals(deletedEvents.get(j))) {
                            events.removeAt(i)
                            deletedEvents.removeAt(j)
                            removedAnEvent = true
                            break
                        }
                    }
                    if (removedAnEvent) {
                        break
                    }
                }
            }
            return events;
        }
    }