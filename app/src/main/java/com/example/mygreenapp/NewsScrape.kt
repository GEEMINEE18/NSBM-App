@file:Suppress("DEPRECATION")

package com.example.mygreenapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import org.jsoup.Jsoup

class NewsScrape(@SuppressLint("StaticFieldLeak") val context: Context) : AsyncTask<Void, Void, String>() {

    // Initialize arrays for storing information from the website
    private var imgList = ArrayList<String>()
    private var titleList = ArrayList<String>()
    private var descriptionList = ArrayList<String>()
    private var confirmCount = 0
    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()

        //configure progress dialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Retrieving news...")
        progressDialog.setCanceledOnTouchOutside(false)

        //show progress
        progressDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): String? {
        val doc = Jsoup.connect("https://www.nsbm.ac.lk/news/").get()
        val allInfo = doc.getElementsByClass("col-sm-6 col-lg-4 blog-isotope-item kl-blog-column")
        //println(allInfo)

        // This loop will look for all the elements in the variable allInfo
        for (i in allInfo) {
            val webImg = i.getElementsByTag("img").attr("src")
            val webTitle = i.getElementsByClass("itemHeader kl-blog-item-header").text()
            val webDescription = i.getElementsByClass("itemIntroText kl-blog-item-content").text()
            // the println function is used only for checking if the values are retrieved or not
            // println(webImg)
            // Add the scraped data into the arrays
            imgList.add(webImg)
            titleList.add(webTitle)
            descriptionList.add(webDescription)
        }

        confirmCount = 1

        // println("The size of title list in web-scrape class is "+titleList.size)

        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        progressDialog.dismiss()
    }

    // Getters

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return imgList
    }

    @JvmName("getTitleList1")
    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return titleList
    }

    fun getDescriptionList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return descriptionList
    }

}