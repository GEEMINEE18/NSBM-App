package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class activity_hockey_club : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hockey_club)

        supportActionBar!!.title = "Hockey Club"
    }
}