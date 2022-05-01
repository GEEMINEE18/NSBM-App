package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView

class CalendarActivity : AppCompatActivity() {
    lateinit var calendarView: CalendarView
    lateinit var dateView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        calendarView = findViewById(R.id.calendarView)
        dateView = findViewById(R.id.txtDay)
        calendarView.setOnDateChangeListener { View, year, month, dayOfMonth ->
            val date = dayOfMonth.toString() + "−" + (month + 1) + "−" + year
            dateView.text = date
            //toAdEvntBtn()
        }


    }
/*
    private fun toAdEvntBtn() {
        val eventBtn = findViewById<Button>(R.id.AdEvntBtn)
        eventBtn.setOnClickListener {
            val intent = Intent(this, AddEvent::class.java)
            startActivity(intent)

        }
    }
    */
}