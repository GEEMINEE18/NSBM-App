package com.example.mygreenapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityBasedClubsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitybased_club)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Activity Based Clubs"

        //This is the back button
        val myBtn = findViewById<Button>(R.id.myButton)
        myBtn.setOnClickListener {
            finish()
        }
    }
}