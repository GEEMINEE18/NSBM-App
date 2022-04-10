package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClubsAndSocietiesActivity : AppCompatActivity(), ButtonAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clubandsocieties)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Clubs and Societies"

        // Get the file Location and name where Json File gets stored
        val fileName = filesDir.path + "/CnSData.json"
        // Read the written Json File
        var reader = ReadWriteJSON(fileName)
        reader.readJSONData()
        var imgList = reader.getImageList()
        var titleList = reader.getTitleList()
        var urlList = reader.getUrlList()
        var listSize = reader.getListSize()

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.buttonRecyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ButtonViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 0 until listSize) {
            data.add(ButtonViewModel(imgList[i], titleList[i], urlList[i]))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = ButtonAdapter(data, this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    override fun onItemClick(position: Int, text: String, url: String) {
        Toast.makeText(this, "$text button clicked", Toast.LENGTH_SHORT).show()
        println("Item $position $text clicked")

        var hiddenUrl = url

        // Create a session and send value
        val intent = Intent(this,ClubsAndSocietiesSecond::class.java);
        intent.putExtra("url", hiddenUrl)
        startActivity(intent);
    }
}