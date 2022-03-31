package com.example.mygreenapp

import android.os.AsyncTask
import org.jsoup.Jsoup

class ButtonScrape() : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var titleList = ArrayList<String>()
    private var imageList = ArrayList<String>()
    private var confirmCount =0

    override fun doInBackground(vararg params: Void?): String? {
        var doc = Jsoup.connect("https://www.nsbm.ac.lk/life-at-nsbm/clubs-societies/").get()
        var allInfo = doc.getElementsByClass("club-item-wrapper")
        //println(allInfo)

        // This loop will look for all the elements in the variable allInfo
        for (i in allInfo) {
            var webTitle = i.getElementsByClass("club-title").text()
            var webImage = i.getElementsByTag("img").attr("src")
            // Add the scraped data into the arrays
            titleList.add(webTitle)
            imageList.add(webImage)
        }

        confirmCount = 1

        // println("The size of title list in webscrape class is "+titleList.size)

        return null
    }

    // Getters

    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return titleList
    }

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return imageList
    }

}