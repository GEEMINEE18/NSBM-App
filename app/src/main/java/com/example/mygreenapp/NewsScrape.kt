package com.example.mygreenapp

import android.app.ProgressDialog
import android.os.AsyncTask
import org.jsoup.Jsoup

class NewsScrape() : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var imgList = ArrayList<String>()
    private var titleList = ArrayList<String>()
    private var descriptionList = ArrayList<String>()
    private var confirmCount = 0

    override fun doInBackground(vararg params: Void?): String? {
        var doc = Jsoup.connect("https://www.nsbm.ac.lk/news/").get()
        var allInfo = doc.getElementsByClass("col-sm-6 col-lg-4 blog-isotope-item kl-blog-column")
        //println(allInfo)

        // This loop will look for all the elements in the variable allInfo
        for (i in allInfo) {
            var webImg = i.getElementsByTag("img").attr("src")
            var webTitle = i.getElementsByClass("itemHeader kl-blog-item-header").text()
            var webDescription = i.getElementsByClass("itemIntroText kl-blog-item-content").text()
            // the println function is used only for checking if the values are retrieved or not
            // println(webImg)
            // Add the scraped data into the arrays
            imgList.add(webImg)
            titleList.add(webTitle)
            descriptionList.add(webDescription)
        }

        confirmCount = 1

        // println("The size of title list in webscrape class is "+titleList.size)

        return null
    }

    // Getters

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return imgList
    }

    @JvmName("getTitleList1")
    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return titleList
    }

    fun getDescriptionList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return descriptionList
    }

}