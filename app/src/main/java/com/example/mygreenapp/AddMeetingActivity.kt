package com.example.mygreenapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mygreenapp.databinding.ActivityAddMeetingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_meeting.*
import java.text.SimpleDateFormat
import java.util.*


class AddMeetingActivity : AppCompatActivity() {

    //Database variable declaration
    private lateinit var binding: ActivityAddMeetingBinding
    private lateinit var meetingNode: DatabaseReference
    private lateinit var userNode:DatabaseReference
    private lateinit var txtMeetingDate: TextView

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Add New Meeting"

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        txtMeetingDate = findViewById(R.id.txtMeetingDate)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener {view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayofMonth)
            updateLabel(myCalendar)

        }

        //Meeting Time Text View pops up the Time Picker to select the Meeting Date
        txtMeetingTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker,hour ,minuite->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minuite)
                //set time to Text View
                txtMeetingTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        //Meeting Date Text View pops up the calendar to select the Meeting Date
        txtMeetingDate.setOnClickListener{
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnAddMeetingDb.setOnClickListener {
            val meetingTitle = binding.txtMeetingTitle.text.toString()
            val meetingDesc = binding.txtMeetingDesc.text.toString()
            val meetingVenue = binding.txtVenue.text.toString()
            val meetingDate = binding.txtMeetingDate.text.toString()
            val meetingTime = binding.txtMeetingTime.text.toString()

            meetingNode = FirebaseDatabase.getInstance().getReference("Meeting")
            meetingNode.get().addOnSuccessListener { it ->
                Log.i("firebase", "Got value ${it.value}")
                //get the meeting number nodes to an array and splitting it up to count
                var array = it.value.toString()
                // " }, " To get only the outer nodes
                var delimiter = "},"
                val getMax = array.split(delimiter).size + 1
                val max = getMax.toString()
                print(max)

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val userId = firebaseUser.uid

                //Code to get if userLogin variable true or false from the database
                userNode = Firebase.database.reference
                userNode.child("User").child(userId).child("hosting").get().addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")
                    val clubName = it.value.toString()


                    val meeting = Meeting(
                        meetingTitle,
                        meetingDesc,
                        meetingVenue,
                        meetingDate,
                        meetingTime,
                        clubName
                    )
                    meetingNode.child(max).setValue(meeting).addOnSuccessListener {

                        binding.txtMeetingTitle.text.clear()
                        binding.txtMeetingDesc.text.clear()
                        binding.txtVenue.text.clear()
                        binding.txtMeetingDate.text = ""
                        binding.txtMeetingTime.text = ""

                        Toast.makeText(this, "Meeting successfully added", Toast.LENGTH_SHORT)
                            .show()

                    }.addOnFailureListener {

                        Toast.makeText(this, "Failed to add meeting", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat,Locale.UK)
        txtMeetingDate.text = sdf.format(myCalendar.time)
    }
}

