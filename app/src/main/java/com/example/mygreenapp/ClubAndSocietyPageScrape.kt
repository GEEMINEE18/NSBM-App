package com.example.mygreenapp

import android.os.AsyncTask
import org.jsoup.Jsoup

class ClubAndSocietyPageScrape(url: String) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var title = String()
    private var bannerImage = String()
    private var logoImage = String()
    private var description1 = String()
    private var description2 = String()
    private var confirmCount = 0
    private var url = url

    override fun doInBackground(vararg params: Void?): String? {

        var doc = Jsoup.connect(url).get()

        bannerImage = doc.getElementsByTag("rs-sbg").attr("src")
        logoImage = doc.getElementsByClass("image-boxes-img img-responsive cover-fit-img").attr("src")
        description1 = doc.getElementsByClass("eluid688ce365            col-md-6 col-sm-6   znColumnElement").text()
        description2 = doc.getElementsByClass("eluid51b8f3f9            col-md-6 col-sm-6   znColumnElement").text()

        confirmCount = 1

        println(bannerImage+logoImage+description1+description2)

        return null
    }

    // Getters

    fun getBannerImage(): String {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return bannerImage
    }

    fun getLogoImage(): String {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return logoImage
    }

    fun getDescription1(): String {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return description1
    }

    fun getDescription2(): String {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return description2
    }

}