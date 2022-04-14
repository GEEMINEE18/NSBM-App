package com.example.mygreenapp

import android.os.AsyncTask
import org.jsoup.Jsoup

class CnSScrapeSecond(private var previousUrlList: ArrayList<String>) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var titleList = ArrayList<String>()
    private var imageList = ArrayList<String>()
    private var urlList = ArrayList<String>()
    // URL list which contains data on which parent is the current URL from
    private var parentUrlList = ArrayList<String>()

    private var confirmCount =0
    // private var nameString = clubCategory?.lowercase()

    override fun doInBackground(vararg params: Void?): String? {

        // Nested loop to take all the data inside clubs and societies page 2
        for (i in 0 until previousUrlList.size) {
            var doc = Jsoup.connect(previousUrlList[i]).get()
            var allInfo = doc.getElementsByClass("sc-content-wrapper")
            //println(allInfo)

            // This loop will look for all the elements in the variable allInfo
            for (j in allInfo) {
                val webTitle = j.getElementsByTag("h3").text()
                val webImage = j.getElementsByTag("img").attr("src")
                val webUrl = j.getElementsByTag("a").attr("href")
                // Add the scraped data into the arrays
                titleList.add(webTitle)
                imageList.add(webImage)
                urlList.add(webUrl)
                parentUrlList.add(previousUrlList[i])

                //println(urlList)
            }
        }

        confirmCount = 1

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

    fun getParentUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return parentUrlList
    }

}