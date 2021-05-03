package com.example.myfirstapp

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
import java.util.*

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var serverAddress = findViewById<EditText>(R.id.server_address)


        val serverStatus = findViewById<TextView>(R.id.server_status2)
        val url: String = "https://pokeapi.co/api/v2/pokemon/ditto"//serverAddress.getText() as String

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                //serverStatus.setText(response.toString())
                serverStatus.setText("OK")
            },
            Response.ErrorListener { serverStatus.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        // Now that we have obtained activities.json, read it.
        var activities: ArrayList<Goal>
        val gson = Gson()
        // Check if activities.json exists
        val file =  File(getApplicationContext().filesDir, "activities.json");
        if(file.exists()) {
            // Exists, get json data from it
            val inputStream: InputStream = File(getApplicationContext().filesDir, "activities.json").inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val myType = object : TypeToken<List<Goal>>() {}.type
            activities = gson.fromJson<ArrayList<Goal>>(inputString, myType) // convert from json to Goals array
        }
        else {
            // Does not exist
            activities = ArrayList<Goal>()
        }
        // Now read goals.json
        var goals: ArrayList<Goal>
        // Check if activities.json exists
        val file2 =  File(getApplicationContext().filesDir, "goals.json");
        //file2.readLines()
        if(file2.exists()) {
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

        var changesMadeToGoals: Boolean = false
        // Now we have to mark goals as met or not.
        for (i in goals.indices) {
            if (!goals.get(i).goalMet) {
                for (j in activities.indices) {
                    // Check if new activity can check off this goal.
                    if ((goals.get(i).goalId == activities.get(j).goalId) && (activities.get(j).date <= goals.get(
                            i
                        ).date) && !goals.get(i).goalMet) {
                        if (goals.get(i).goalId == 0) {
                            if (activities.get(j).target <= goals.get(i).target) {
                                goals.get(i).goalMet = true
                                changesMadeToGoals = true
                            }
                        }
                        else {
                            if (activities.get(j).target >= goals.get(i).target) {
                                goals.get(i).goalMet = true
                                changesMadeToGoals = true
                            }
                        }
                    }
                }
            }
        }
        // Rewrite goals.json with new changes made to it, if any changes were made
        if (changesMadeToGoals) {
            val jsonOutput = gson.toJson(goals)

            val filename = "goals.json"
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(jsonOutput.toByteArray())
            }
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

    /** Called when the user taps the Add Goals button */
    fun viewProgress(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, ViewProgressActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun settings(view: View) {
        // Do something in response to button
        //val editText = findViewById<EditText>(R.id.editText)
        val message = "."//editText.text.toString()
        val intent = Intent(this, MySettings::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}