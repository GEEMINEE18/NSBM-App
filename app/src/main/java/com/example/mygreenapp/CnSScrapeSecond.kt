@file:Suppress("DEPRECATION")

package com.example.mygreenapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.content.Context
import org.jsoup.Jsoup

class CnSScrapeSecond(@SuppressLint("StaticFieldLeak") val context: Context, private var previousUrlList: ArrayList<String>) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var titleList = ArrayList<String>()
    private var imageList = ArrayList<String>()
    private var urlList = ArrayList<String>()
    // URL list which contains data on which parent is the current URL from
    private var parentUrlList = ArrayList<String>()
    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    private var confirmCount =0
    // private var nameString = clubCategory?.lowercase()

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()

        //configure progress dialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Retrieving Clubs and Societies second page data...")
        progressDialog.setCanceledOnTouchOutside(false)

        //show progress
        progressDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): String? {

        // Nested loop to take all the data inside clubs and societies page 2
        for (i in 0 until previousUrlList.size) {
            val doc = Jsoup.connect(previousUrlList[i]).get()
            val allInfo = doc.getElementsByClass("sc-content-wrapper")
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

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        progressDialog.dismiss()
    }

    // Getters

    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return titleList
    }

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return imageList
    }

    fun getUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return urlList
    }

    fun getParentUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return parentUrlList
    }

}