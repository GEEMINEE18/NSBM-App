package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ClubAndSocietiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clubandsocieties)

        val sportsBtn = findViewById<Button>(R.id.sportsBtn)
        val religiousBtn = findViewById<Button>(R.id.religiousBtn)
        val internationalBtn = findViewById<Button>(R.id.internationalBtn)

        sportsBtn.setOnClickListener {
            val intent = Intent (this,SportsClubsActivity::class.java)
            startActivity(intent)
        }

        religiousBtn.setOnClickListener {
            val intent = Intent(this,ReligiousClubActivity::class.java)
            startActivity(intent)
        }

        internationalBtn.setOnClickListener {
            val intent = Intent (this,InternationalClubsActivity::class.java)
            startActivity(intent)
        }
    }
}