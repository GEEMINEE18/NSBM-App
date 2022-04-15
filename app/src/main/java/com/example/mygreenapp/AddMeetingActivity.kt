package com.example.mygreenapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mygreenapp.databinding.ActivityAddMeetingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_meeting.*
import java.text.SimpleDateFormat
import java.util.*


class AddMeetingActivity : AppCompatActivity() {

    //Database variable declaration
    private lateinit var binding: ActivityAddMeetingBinding
    private lateinit var database: DatabaseReference
    private lateinit var txtMeetingDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Add New Meeting"


        txtMeetingDate = findViewById(R.id.txtMeetingDate)

        val mycalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener {view, year, month, dayofMonth ->
            mycalendar.set(Calendar.YEAR,year)
            mycalendar.set(Calendar.MONTH,month)
            mycalendar.set(Calendar.DAY_OF_MONTH,dayofMonth)
            updateLable(mycalendar)

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
            DatePickerDialog(this,datePicker, mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnAddMeetingDb.setOnClickListener {
            val meetingTitle = binding.txtMeetingTitle.text.toString()
            val meetingDesc = binding.txtMeetingDesc.text.toString()
            val meetingVenue = binding.txtVenue.text.toString()
            val meetingDate = binding.txtMeetingDate.text.toString()
            val meetingTime = binding.txtMeetingTime.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Meeting")
            val max = database.child("Meeting")
            print(max)
            val Meeting = Meeting(meetingTitle, meetingDesc, meetingVenue, meetingDate, meetingTime)
            database.setValue(Meeting).addOnSuccessListener {

                binding.txtMeetingTitle.text.clear()
                binding.txtMeetingDesc.text.clear()
                binding.txtVenue.text.clear()
                binding.txtMeetingDate.text = ""
                binding.txtMeetingTime.text = ""

                Toast.makeText(this, "Meeting successfully added", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "Failed to add meeting", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateLable(mycalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat,Locale.UK)
        txtMeetingDate.text = sdf.format(mycalendar.time)
    }
}