package com.example.mygreenapp

import android.os.AsyncTask
import org.jsoup.Jsoup

class CnSScrapeFinal(private var previousUrlList: ArrayList<String>) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var bannerImageList = ArrayList<String>()
    private var logoImageList = ArrayList<String>()
    private var description1List = ArrayList<String>()
    private var description2List = ArrayList<String>()
    // URL list which contains data on which parent is the current URL from
    private var parentUrlList = ArrayList<String>()

    private var confirmCount = 0

    override fun doInBackground(vararg params: Void?): String? {

        // Nested loop to take all the data inside clubs and societies page 2
        for (i in 0 until previousUrlList.size) {
            var doc = Jsoup.connect(previousUrlList[i]).get()
            // Define all the image, text elements
            var imageInfo = doc.getElementsByClass("widget widget_revslider")
            var descriptionInfo = doc.getElementsByClass("znColumnElement-innerContent")

            val logoImage = doc.getElementsByClass("image-boxes-img img-responsive cover-fit-img").attr("src")
            val description2 = doc.getElementsByClass("eluid51b8f3f9            col-md-6 col-sm-6   znColumnElement").text()

            // These two loops are defined to make sure only the correct info is retrieved
            for (j in imageInfo) {
                val bannerImage = ("https:"+j.getElementsByTag("img").attr("src"))
                bannerImageList.add(bannerImage)
            }
            for (k in descriptionInfo) {
                val description1 = doc.getElementsByTag("p").text()
                description1List.add(description1)
            }

            logoImageList.add(logoImage)
            description2List.add(description2)
            parentUrlList.add(previousUrlList[i])
        }

        confirmCount = 1

        return null
    }

    // Getters

    fun getBannerImage(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return bannerImageList
    }

    fun getLogoImage(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return logoImageList
    }

    fun getDescription1(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return description1List
    }

    fun getDescription2(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return description2List
    }

    fun getParentUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return parentUrlList
    }

}