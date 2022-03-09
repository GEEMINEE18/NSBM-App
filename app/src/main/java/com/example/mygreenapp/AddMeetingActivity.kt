package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddMeetingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meeting)
        supportActionBar!!.title = "Add New Meeting"
    }
}