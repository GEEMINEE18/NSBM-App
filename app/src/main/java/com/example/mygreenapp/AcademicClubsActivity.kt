package com.example.mygreenapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AcademicClubsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.academicclub_mainpage)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Academic Clubs"

        //This is the back button
        val myBtn = findViewById<Button>(R.id.myButton)
        myBtn.setOnClickListener {
            finish()
        }
    }
}