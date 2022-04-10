package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ClubsAndSocietiesFinal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clubs_and_societies_final)

        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var backButton = findViewById<Button>(R.id.btnBack)

        backButton.setOnClickListener{
            finish()
        }

        // Get value from ClubAndSocietiesActivity
        val hiddenUrl = intent.getStringExtra("url")
        val pageTitle = intent.getStringExtra("title")

        // Get the file Location and name where Json File gets stored
        val fileName = filesDir.path + "/CnSDataFinal.json"
        // Read the written Json File
        var reader = ReadWriteJSON(fileName)
        // Send the url of the clicked parent button to the ReadWriteJSON file
        if (hiddenUrl != null) {
            reader.readJSONDataCnSFinal(hiddenUrl)
        }

        var bannerImage = reader.getBannerImage()
        var logoImage = reader.getLogoImage()
        var firstDescription = reader.getDescription1()
        var secondDescription = reader.getDescription2()

        var imgBanner = findViewById<ImageView>(R.id.imgCSBanner)
        var imgLogo = findViewById<ImageView>(R.id.imgCSLogo)

        Glide.with(this@ClubsAndSocietiesFinal).load(bannerImage).into(imgBanner)
        Glide.with(this@ClubsAndSocietiesFinal).load(logoImage).into(imgLogo)
        findViewById<TextView>(R.id.txtCSTitle).text = pageTitle
        findViewById<TextView>(R.id.txtDescriptionContent1).text = firstDescription
        findViewById<TextView>(R.id.txtDescriptionContent2).text = secondDescription
    }
}