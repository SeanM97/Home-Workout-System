package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class ViewProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_progress)

        val tex2 = findViewById<ProgressBar>(R.id.progressBar2)
        tex2.setProgress(50)

        val tex3 = findViewById<ProgressBar>(R.id.progressBar3)
        tex3.setProgress(50)

        val tex4 = findViewById<ProgressBar>(R.id.progressBar4)
        tex4.setProgress(50)
    }
}