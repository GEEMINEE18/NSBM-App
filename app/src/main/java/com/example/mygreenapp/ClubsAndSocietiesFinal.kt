package com.example.mygreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ClubsAndSocietiesFinal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clubs_and_societies_final)

        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get value from ClubAndSocietiesActivity
        val hiddenUrl = intent.getStringExtra("url")

        // WebScrape

        var webScrape = ClubAndSocietyPageScrape(hiddenUrl.toString())
        webScrape.execute()

        var bannerImage = webScrape.getBannerImage()
        var logoImage = webScrape.getLogoImage()
        var firstDescription = webScrape.getDescription1()
        var secondDescription = webScrape.getDescription2()

        var imgBanner = findViewById<ImageView>(R.id.imgCSBanner)
        var imgLogo = findViewById<ImageView>(R.id.imgCSLogo)

        Glide.with(this@ClubsAndSocietiesFinal).load(bannerImage).into(imgBanner)
        Glide.with(this@ClubsAndSocietiesFinal).load(logoImage).into(imgLogo)
        findViewById<TextView>(R.id.txtDescriptionContent1).text = firstDescription
        findViewById<TextView>(R.id.txtDescriptionContent2).text = secondDescription
    }
}