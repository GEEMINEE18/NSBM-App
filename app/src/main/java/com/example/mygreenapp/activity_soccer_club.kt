package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class activity_soccer_club : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soccer_club)

        supportActionBar!!.title = "Soccer Club"
    }
}