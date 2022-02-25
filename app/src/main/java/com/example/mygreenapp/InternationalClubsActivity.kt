package com.example.mygreenapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class InternationalClubsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.internationalclub_mainpage)

        //This is the back button
        val myBtn = findViewById<Button>(R.id.myButton)
        myBtn.setOnClickListener {
            finish()
        }
    }
}