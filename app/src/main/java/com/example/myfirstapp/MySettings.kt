package com.example.myfirstapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream
import java.util.ArrayList

class MySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_settings)

        // Get server address stored
        val gson = Gson()
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
        val serverStatus = findViewById<EditText>(R.id.server_address)
        serverStatus.setText(serverAddr)

    }

    fun testServer(v: View) {
        val serverStatus = findViewById<TextView>(R.id.textView12)
        serverStatus.setText("Connecting...")
        val urlText = findViewById<TextView>(R.id.server_address)
        val url = urlText.getText().toString()//"http://76.170.169.89:8080/api/v1/scale"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var sd: String
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.

                sd = response.toString().toString().replace("goalID", "goalId")
                sd = sd.replace(",\"goalId\":", "000,\"goalId\":")

                serverStatus.setText(sd)
            },
            Response.ErrorListener { e ->
                serverStatus.text = e.toString()
                //serverStatus.text = "That didn't work!" })
            })
        // Add the request to the RequestQueue.

        //serverStatus.setText(stringRequest.toString())

        queue.add(stringRequest)
    }

    fun updateServer(v: View) {
        val urlText = findViewById<TextView>(R.id.server_address)
        var updatedAddr: String = urlText.getText().toString()

        val gson = Gson()

        //gson.toJson(newGoal, FileWriter(GOALS_FILE))

        // Convert from Goals array to json
        val jsonOutput = gson.toJson(updatedAddr)

        val filename = SERVER_ADDRESS_FILE
        //val fileContents = "Hello world!"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(jsonOutput.toByteArray())
        }

        val serverStatus = findViewById<TextView>(R.id.textView12)
        serverStatus.setText(jsonOutput)
        //tex.setText(jsonOutput)
    }
}