package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SportsClubsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sportsclub_mainpage)

        //This is the back button
        val myBtn = findViewById<Button>(R.id.myButton)
        myBtn.setOnClickListener {
            finish()
        }
    }

}