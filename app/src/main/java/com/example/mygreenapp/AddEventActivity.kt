package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        supportActionBar!!.title = "Add New Event"
    }
}