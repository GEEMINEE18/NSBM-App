package com.example.mygreenapp

import android.os.AsyncTask
import org.jsoup.Jsoup

class ButtonScrapeSecond(url: String) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var titleList = ArrayList<String>()
    private var imageList = ArrayList<String>()
    private var urlList = ArrayList<String>()
    private var confirmCount =0
    private var url = url
    // private var nameString = clubCategory?.lowercase()

    override fun doInBackground(vararg params: Void?): String? {

        // This is the deciding factor of the separator
        /*var delimiter = " "

        val parts = nameString?.split(delimiter)
        val part1 = parts?.get(0)
        val part

        println(parts)*/

        var doc = Jsoup.connect(url).get()
        var allInfo = doc.getElementsByClass("sc-content-wrapper")
        //println(allInfo)

        // This loop will look for all the elements in the variable allInfo
        for (i in allInfo) {
            var webTitle = i.getElementsByTag("h3").text()
            var webImage = i.getElementsByTag("img").attr("src")
            var webUrl = i.getElementsByTag("a").attr("href")
            // Add the scraped data into the arrays
            titleList.add(webTitle)
            imageList.add(webImage)
            urlList.add(webUrl)
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

    fun getUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return urlList
    }

}