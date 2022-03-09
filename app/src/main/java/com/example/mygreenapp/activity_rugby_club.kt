package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class activity_rugby_club : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rugby_club)

        supportActionBar!!.title = "Rugby Club"
    }
}