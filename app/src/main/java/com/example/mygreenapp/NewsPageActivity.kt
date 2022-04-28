package com.example.mygreenapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsPageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_page)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "News"

        val reader = NewsReaderWriter(this)

        val titleList = reader.readFromTitleArray()
        val descriptionList = reader.readFromDescriptionArray()
        val imageList = reader.readFromImageArray()

        val listSize = titleList.size

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<NewsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 0 until listSize) {
            data.add(NewsViewModel(imageList[i], titleList[i], descriptionList[i]))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = NewsAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}