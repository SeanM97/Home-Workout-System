package com.example.myfirstapp

data class Goal(val goalId: Int, val target: Int, val date: Long, val hours: Int, val minutes: Int, val isAm: Boolean, var goalMet: Boolean)