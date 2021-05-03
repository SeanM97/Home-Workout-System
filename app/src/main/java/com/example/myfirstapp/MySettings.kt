package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_settings)
    }

    fun testServer(v: View) {
        val serverStatus = findViewById<TextView>(R.id.textView12)
        val urlText = findViewById<TextView>(R.id.server_address)
        val url = urlText.text.toString()//"https://pokeapi.co/api/v2/pokemon/ditto"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                serverStatus.setText(response.toString())
            },
            Response.ErrorListener { serverStatus.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}