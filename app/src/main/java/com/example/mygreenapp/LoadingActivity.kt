@file:Suppress("DEPRECATION")

package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Take Admin variables from Login activity and redirect to Main
        val host = intent.getStringExtra("host")
        val userCurrent = intent.getStringExtra("userCurrent")

        // If already synced, go to MainActivity.kt

        // Create ReaderWriter object to check if user had already synced today
        val newsWriter = NewsReaderWriter(this)

        newsWriter.checkFiles()

        val yearNow = LocalDateTime.now().year
        val dayNow = LocalDateTime.now().dayOfYear
        val yearLast = newsWriter.loadLastSyncYear()
        val dayLast = newsWriter.loadLastSyncDay()

        if (yearNow == yearLast && dayNow == dayLast) {
            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            intent.putExtra("host", host)
            intent.putExtra("userCurrent", userCurrent)
            startActivity(intent)
            finish()
        }


        val syncButton = findViewById<Button>(R.id.btnSync)
        val enterButton = findViewById<Button>(R.id.btnEnter)
        val syncText = findViewById<TextView>(R.id.txtSyncDialog)

        syncButton.setOnClickListener {
            scrape()
            syncText.text = getString(R.string.web_sync_completed)
            enterButton.isEnabled = true
            enterButton.isClickable = true
        }

        enterButton.setOnClickListener {
            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            intent.putExtra("host", host)
            intent.putExtra("userCurrent", userCurrent)
            startActivity(intent)
            finish()
        }
    }

    private fun scrape() {

        // Create ReaderWriter object
        val newsWriter = NewsReaderWriter(this)

        newsWriter.checkFiles()

        val yearNow = LocalDateTime.now().year
        val dayNow = LocalDateTime.now().dayOfYear
        val yearLast = newsWriter.loadLastSyncYear()
        val dayLast = newsWriter.loadLastSyncDay()

        // yearNow != yearLast || dayNow != dayLast
        if (yearNow != yearLast || dayNow != dayLast)
        {
            // News Scrape

            val webScrape = NewsScrape(this@LoadingActivity)
            webScrape.execute()

            // Initialize arrays for storing information from the website
            val newsImageList = webScrape.getImageList()
            val newsTitleList = webScrape.getTitleList()
            val newsDescriptionList = webScrape.getDescriptionList()

            // Write to the text file
            newsWriter.writeToImageArray(newsImageList)
            newsWriter.writeToTitleArray(newsTitleList)
            newsWriter.writeToDescriptionArray(newsDescriptionList)

            // Write the last sync date to the files if web-sync completed
            newsWriter.saveCurrentYear()
            newsWriter.saveCurrentDay()

            // CnS Page 1 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileName = filesDir.path + "/CnSData.json"
            // call write Json method
            val jsonWriter = JSONReaderWriter(cnsFileName)

            // JSON for ClubsNSocieties
            val cnsScrape = CnSScrapeFirst(this)
            cnsScrape.execute()

            // Initialize arrays for storing information from the website
            val cnsImageList = cnsScrape.getImageList()
            val cnsTitleList = cnsScrape.getTitleList()
            val cnsUrlList = cnsScrape.getUrlList()

            val cnsListSize = cnsTitleList.size

            jsonWriter.writeJSONtoFile(cnsFileName, cnsListSize, cnsImageList, cnsTitleList, cnsUrlList)

            // CnS Page 2 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileNameSecond = filesDir.path + "/CnSDataSecond.json"
            // call write Json method
            val jsonWriterSecond = JSONReaderWriter(cnsFileNameSecond)

            // JSON for ClubsNSocieties
            val cnsScrapeSecond = CnSScrapeSecond(this, cnsUrlList)
            cnsScrapeSecond.execute()

            // Initialize arrays for storing information from the website
            val cnsImageListSecond = cnsScrapeSecond.getImageList()
            val cnsTitleListSecond = cnsScrapeSecond.getTitleList()
            val cnsUrlListSecond = cnsScrapeSecond.getUrlList()
            val cnsParentUrlListSecond = cnsScrapeSecond.getParentUrlList()

            val cnsListSizeSecond = cnsTitleListSecond.size

            jsonWriterSecond.writeJSONtoFileWithURL(cnsFileNameSecond, cnsListSizeSecond, cnsImageListSecond, cnsTitleListSecond, cnsUrlListSecond, cnsParentUrlListSecond)

            // CnS Page 3 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileNameFinal = filesDir.path + "/CnSDataFinal.json"
            // call write Json method
            val jsonWriterFinal = JSONReaderWriter(cnsFileNameFinal)

            // JSON for ClubsNSocieties
            val cnsScrapeFinal = CnSScrapeFinal(this, cnsUrlListSecond)
            cnsScrapeFinal.execute()

            // Initialize arrays for storing information from the website
            val cnsBannerListFinal = cnsScrapeFinal.getBannerImage()
            val cnsLogoListFinal = cnsScrapeFinal.getLogoImage()
            val cnsDescription1ListFinal = cnsScrapeFinal.getDescription1()
            val cnsDescription2ListFinal = cnsScrapeFinal.getDescription2()
            val cnsParentUrlListFinal = cnsScrapeFinal.getParentUrlList()

            val cnsListSizeFinal = cnsBannerListFinal.size

            jsonWriterFinal.writeJSONtoFileCnSFinal(cnsFileNameFinal, cnsListSizeFinal, cnsBannerListFinal, cnsLogoListFinal, cnsDescription1ListFinal, cnsDescription2ListFinal, cnsParentUrlListFinal)
        }
    }
}