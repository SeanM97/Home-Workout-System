package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream
import java.util.ArrayList

class ViewProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_progress)

        val tex2 = findViewById<ProgressBar>(R.id.progressBar2)
        val tex3 = findViewById<ProgressBar>(R.id.progressBar3)
        val tex4 = findViewById<ProgressBar>(R.id.progressBar4)

        var goals: ArrayList<Goal>
        var goalsToDisplay: ArrayList<Goal>

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

        if (goals.size > 0) {
            var pastWeekTotal = 0
            var pastMonthTotal = 0
            var pastYearTotal = 0

            var pastWeekMet = 0
            var pastMonthMet = 0
            var pastYearMet = 0

            val currentTime: Long = System.currentTimeMillis()
            val oneWeek: Long = 604800000
            val oneMonth: Long = 2591568000
            val oneYear: Long = 31449600000

            for (i in goals.indices) {
                if ((currentTime - oneWeek) < goals.get(i).date) {
                    pastWeekTotal++
                    pastMonthTotal++
                    pastYearTotal++
                    if (goals.get(i).goalMet) {
                        pastWeekMet++
                        pastMonthMet++
                        pastYearMet++
                    }
                } else if ((currentTime - oneMonth) < goals.get(i).date) {
                    pastMonthTotal++
                    pastYearTotal++
                    if (goals.get(i).goalMet) {
                        pastMonthMet++
                        pastYearMet++
                    }
                } else if ((currentTime - oneYear) < goals.get(i).date) {
                    pastYearTotal++
                    if (goals.get(i).goalMet) {
                        pastYearMet++
                    }
                }
            }

            tex2.setProgress((pastWeekMet * 100) / pastWeekTotal)
            tex3.setProgress((pastMonthMet * 100) / pastMonthTotal)
            tex4.setProgress((pastYearMet * 100) / pastYearTotal)

            val text1 = findViewById<TextView>(R.id.pastWeek)
            val text2 = findViewById<TextView>(R.id.pastMonth)
            val text3 = findViewById<TextView>(R.id.pastYear)

            text1.setText("" + pastWeekMet + " of " + pastWeekTotal + " Goals Met")
            text2.setText("" + pastMonthMet + " of " + pastMonthTotal + " Goals Met")
            text3.setText("" + pastYearMet + " of " + pastYearTotal + " Goals Met")
        }
        else {
            tex2.setProgress(0)
            tex3.setProgress(0)
            tex4.setProgress(0)

            val text1 = findViewById<TextView>(R.id.pastWeek)
            val text2 = findViewById<TextView>(R.id.pastMonth)
            val text3 = findViewById<TextView>(R.id.pastYear)

            text1.setText("0 of 0 Goals Met")
            text2.setText("0 of 0 Goals Met")
            text3.setText("0 of 0 Goals Met")
        }
    }
}