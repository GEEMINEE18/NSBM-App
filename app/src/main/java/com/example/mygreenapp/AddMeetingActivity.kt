package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.mygreenapp.databinding.ActivityAddMeetingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_meeting.*

class AddMeetingActivity : AppCompatActivity() {

    //Database variable declaration
    private lateinit var binding: ActivityAddMeetingBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Add New Meeting"
        OnClickTime()

        txtMeetingTitle.setOnClickListener {

        }

        binding.btnAddMeetingDb.setOnClickListener {
            val meetingTitle = binding.txtMeetingTitle.text.toString()
            val meetingDesc = binding.txtMeetingDesc.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Meeting")
            val Meeting = Meeting(meetingTitle, meetingDesc)
            database.child(meetingTitle).setValue(Meeting).addOnSuccessListener {

                binding.txtMeetingTitle.text.clear()
                binding.txtMeetingDesc.text.clear()

                Toast.makeText(this, "Meeting successfully added", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "Failed to add meeting", Toast.LENGTH_SHORT).show()

            }

        }


    }

        private fun OnClickTime() {
            val textView = findViewById<TextView>(R.id.textView)
            val timePicker = findViewById<TimePicker>(R.id.timePicker)
            timePicker.setOnTimeChangedListener { _, hour, minute ->
                var hour = hour
                var am_pm = ""
                // AM_PM decider logic
                when {
                    hour == 0 -> {
                        hour += 12
                        am_pm = "AM"
                    }
                    hour == 12 -> am_pm = "PM"
                    hour > 12 -> {
                        hour -= 12
                        am_pm = "PM"
                    }
                    else -> am_pm = "AM"
                }
                if (textView != null) {
                    val hour = if (hour < 10) "0" + hour else hour
                    val min = if (minute < 10) "0" + minute else minute
                    // display format of time
                    val msg = "Time is: $hour : $min $am_pm"
                    textView.text = msg
                    textView.visibility = ViewGroup.VISIBLE
            }
        }
    }
}