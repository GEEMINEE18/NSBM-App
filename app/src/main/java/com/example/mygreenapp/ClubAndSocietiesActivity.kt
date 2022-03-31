package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClubAndSocietiesActivity : AppCompatActivity(), ButtonAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clubandsocieties)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Clubs and Societies"

        // Webscrape


        var webScrape = ButtonScrape()
        webScrape.execute()

        // Initialize arrays for storing information from the website
        var imgList = webScrape.getImageList()
        var titleList = webScrape.getTitleList()

        val listSize = titleList.size

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.buttonRecyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ButtonViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 0 until listSize) {
            data.add(ButtonViewModel(imgList[i], titleList[i]))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = ButtonAdapter(data, this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter






        // Hardcoded buttons

        /*val sportsBtn = findViewById<Button>(R.id.sportsBtn)
        val religiousBtn = findViewById<Button>(R.id.religiousBtn)
        val internationalBtn = findViewById<Button>(R.id.internationalBtn)
        val activityBtn = findViewById<Button>(R.id.activityBtn)
        val academicBtn = findViewById<Button>(R.id.academicBtn)

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

        activityBtn.setOnClickListener {
            val intent = Intent (this,ActivityBasedClubsActivity::class.java)
            startActivity(intent)
        }

        academicBtn.setOnClickListener {
            val intent = Intent (this,AcademicClubsActivity::class.java)
            startActivity(intent)
        }*/
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        println("Item $position clicked")
        val intent = Intent (this,SportsClubsActivity::class.java)
        startActivity(intent)
    }
}