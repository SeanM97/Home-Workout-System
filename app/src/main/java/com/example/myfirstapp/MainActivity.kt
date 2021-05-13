package com.example.myfirstapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
const val DEFAULT_ADDRESS = "http://76.170.169.89:8080"
const val EVENTS_FILE = "events.json" // All events recorded by server
const val DELETED_EVENTS_FILE = "deleted_events.json" // All deleted events recorded by user
const val GOALS_FILE = "goals.json" // All goals recorded by user
const val SERVER_ADDRESS_FILE = "server_address.json" // Server address

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fileD : File
        // Delete goals file
        //var fileD = File(getApplicationContext().filesDir, GOALS_FILE)
        //fileD.delete();
        // Delete events file
        //fileD = File(getApplicationContext().filesDir, EVENTS_FILE)
        //fileD.delete();
        // Delete deleted events file
        //fileD = File(getApplicationContext().filesDir, DELETED_EVENTS_FILE)
        //fileD.delete();
        // Delete server address file
        //fileD = File(getApplicationContext().filesDir, SERVER_ADDRESS_FILE)
        //fileD.delete();

        //refresh()
        val gson = Gson()

        // Now that we have obtained events.json, read it.
        var events: ArrayList<Goal> = getEvents()

        Log.i("Events size:", "" + events.size)

        // Now read GOALS_FILE
        var goals: ArrayList<Goal>
        // Check if GOALS_FILE exists
        val file2 =  File(getApplicationContext().filesDir, GOALS_FILE);
        if(file2.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream = File(getApplicationContext().filesDir, GOALS_FILE).inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val myType = object : TypeToken<List<Goal>>() {}.type
            goals = gson.fromJson<ArrayList<Goal>>(inputString, myType) // convert from json to Goals array
        }
        else {
            // Does not exist
            goals = ArrayList<Goal>()
        }

        //events = goals;

        //var changesMadeToGoals: Boolean = false

        // Now we have to mark goals as met or not.
        // OLD CODE, DO NOT USE
        /*
        for (i in goals.indices) {
            if (!goals.get(i).goalMet) {
                for (j in events.indices) {
                    // Check if new activity can check off this goal.
                    if ((goals.get(i).goalId == events.get(j).goalId) && (events.get(j).date <= goals.get(i).date) && !goals.get(i).goalMet) {
                        if (goals.get(i).goalId == 0) {
                            if (events.get(j).target <= goals.get(i).target) {
                                goals.get(i).goalMet = true
                                changesMadeToGoals = true
                            }
                        }
                        else {
                            if (events.get(j).target >= goals.get(i).target) {
                                goals.get(i).goalMet = true
                                changesMadeToGoals = true
                            }
                        }
                    }
                }
            }
        }
         */

        // Now we have to mark goals as met or not.
        for (i in goals.indices) {
            goals.get(i).goalMet = false
            for (j in events.indices) {
                // Check if new activity can check off this goal.
                if ((goals.get(i).goalId == events.get(j).goalId) && (events.get(j).date <= goals.get(i).date) && events.get(j).goalMet) {
                    if (goals.get(i).goalId == 0 || goals.get(i).goalId == 5 || goals.get(i).goalId == 7) {
                        if (events.get(j).target <= goals.get(i).target) {
                            events.get(j).goalMet = false
                            goals.get(i).goalMet = true
                            break
                        }
                    }
                    else {
                        if (events.get(j).target >= goals.get(i).target) {
                            events.get(j).goalMet = false
                            goals.get(i).goalMet = true
                            break
                        }
                    }
                }
            }
        }

        // Rewrite GOALS_FILE with new changes made to it, if any changes were made
        val jsonOutput = gson.toJson(goals)

        val filename = GOALS_FILE
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(jsonOutput.toByteArray())
        }
    }

    /** Called when the user taps the Send button */
    /*
    fun sendMessage(view: View) {
        // Do something in response to button
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
    */
    /** Called when the user taps the View Goals button */
    fun viewGoals(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, ViewGoalsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the View Events button */
    fun viewEvents(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, ViewEventsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the Add Goals button */
    fun addGoals(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, AddGoalsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the View Progress button */
    fun viewProgress(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, ViewProgressActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the Settings button */
    fun settings(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, MySettings::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun refresh() {
        val gson = Gson()
        // Check if server address json file has been updated
        var serverAddr: String = ""
        val file3 =  File(getApplicationContext().filesDir, SERVER_ADDRESS_FILE);
        if(file3.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream = File(getApplicationContext().filesDir, SERVER_ADDRESS_FILE).inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val myType = object : TypeToken<String>() {}.type
            serverAddr = gson.fromJson<String>(inputString, myType) // convert from json to String
        }
        else {
            // Does not exist
            serverAddr = DEFAULT_ADDRESS // Default server address
        }
        // End checking server address saved

        val serverStatus = findViewById<TextView>(R.id.server_status2)
        serverStatus.setText("Connecting...")
        val url: String = serverAddr

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var sd = "[{\"date\":1620002920978,\"goalId\":0,\"goalMet\":true,\"hours\":4,isAm\":false,\"minutes\":48,\"target\":5},{\"date\":1620002920978,\"goalId\":0,\"goalMet\":true,\"hours\":4,isAm\":false,\"minutes\":48,\"target\":5}]"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    //serverStatus.setText(response.toString())
                    serverStatus.setText("OK")

                    sd = response.toString().toString().replace("goalID", "goalId")
                    sd = sd.replace(",\"goalId\":", "000,\"goalId\":")

                    //storeServerData(response.toString())
                    storeServerData(sd);
                                          },
                Response.ErrorListener {
                    serverStatus.text = "Failed";
                    storeServerData(sd); // FOR TESTING ONLY, REMOVE FOR ACTUAL THING.
                })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    /** Called when the user taps the Refresh button */
    fun refresh(view: View) {
        refresh()

        /*
        // temporary, remove when server data parsing gets fixed
        var goals: ArrayList<Goal>
        var goalsToDisplay: ArrayList<Goal>

        val gson = Gson()

        // Check if GOALS_FILE exists
        val file = File(getApplicationContext().filesDir, GOALS_FILE);
        //file.readLines()
        if (file.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream =
                File(getApplicationContext().filesDir, GOALS_FILE).inputStream()
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
            for (i in goals.indices) {
                goals.get(i).goalMet = true
            }
        }

        val jsonOutput = gson.toJson(goals)

        val filename = GOALS_FILE
        //val fileContents = "Hello world!"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(jsonOutput.toByteArray())
        }

         */

    }

    private fun storeServerData(serverData: String) {
        Log.i("serverData", serverData)

        val gson = Gson()

        val filename = EVENTS_FILE
        //val fileContents = "Hello world!"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(serverData.toByteArray())
        }

    }

    private fun getEvents(): ArrayList<Goal> {
        val gson = Gson()
        var events: ArrayList<Goal>
        val file = File(getApplicationContext().filesDir, EVENTS_FILE);
        if (file.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream =
                File(getApplicationContext().filesDir, EVENTS_FILE).inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }

            Log.i("tag0", inputString)

            val myType = object : TypeToken<List<Goal>>() {}.type
            events = gson.fromJson<ArrayList<Goal>>(
                inputString,
                myType
            ) // convert from json to Goals array

            Log.i("tag0", inputString)
            for (i in events.indices) {
                Log.i("tag1", events.get(i).toString())
            }

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