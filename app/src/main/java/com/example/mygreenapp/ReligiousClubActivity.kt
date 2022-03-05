package com.example.mygreenapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ReligiousClubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.religiousclub_mainpage)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Religious Clubs"
    }
}