package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        var enterButton = findViewById<Button>(R.id.btnEnter)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var progressDialog = findViewById<TextView>(R.id.txtProgress)

        // Take Admin variables from Login activity and redirect to Main
        val host = intent.getStringExtra("host")
        val userCurrent = intent.getStringExtra("userCurrent")

        enterButton.setOnClickListener {
            progressBar.visibility = VISIBLE
            progressDialog.visibility = VISIBLE

            scrape()

            progressBar.visibility = INVISIBLE
            progressDialog.visibility = INVISIBLE

            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            intent.putExtra("host", host)
            intent.putExtra("userCurrent", userCurrent)
            startActivity(intent)
            finish()
        }
    }

    private fun scrape() {

        // Create ReaderWriter object
        var newsWriter = NewsReaderWriter(this)

        newsWriter.checkFiles()

        var yearNow = LocalDateTime.now().year
        var dayNow = LocalDateTime.now().dayOfYear
        var yearLast = newsWriter.loadLastSyncYear()
        var dayLast = newsWriter.loadLastSyncDay()

        // yearNow != yearLast || dayNow != dayLast
        if (yearNow != yearLast || dayNow != dayLast)
        {
            // News Scrape

            var webScrape = NewsScrape()
            webScrape.execute()

            // Initialize arrays for storing information from the website
            var newsImageList = webScrape.getImageList()
            var newsTitleList = webScrape.getTitleList()
            var newsDescriptionList = webScrape.getDescriptionList()

            // Write to the text file
            newsWriter.writeToImageArray(newsImageList)
            newsWriter.writeToTitleArray(newsTitleList)
            newsWriter.writeToDescriptionArray(newsDescriptionList)

            // Write the last sync date to the files if websync completed
            newsWriter.saveCurrentYear()
            newsWriter.saveCurrentDay()

            // CnS Page 1 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileName = filesDir.path + "/CnSData.json"
            // call write Json method
            var jsonWriter = JSONReaderWriter(cnsFileName)

            // JSON for ClubsNSocieties
            var cnsScrape = CnSScrapeFirst()
            cnsScrape.execute()

            // Initialize arrays for storing information from the website
            var cnsImageList = cnsScrape.getImageList()
            var cnsTitleList = cnsScrape.getTitleList()
            var cnsUrlList = cnsScrape.getUrlList()

            val cnsListSize = cnsTitleList.size

            jsonWriter.writeJSONtoFile(cnsFileName, cnsListSize, cnsImageList, cnsTitleList, cnsUrlList)

            // CnS Page 2 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileNameSecond = filesDir.path + "/CnSDataSecond.json"
            // call write Json method
            var jsonWriterSecond = JSONReaderWriter(cnsFileNameSecond)

            // JSON for ClubsNSocieties
            var cnsScrapeSecond = CnSScrapeSecond(cnsUrlList)
            cnsScrapeSecond.execute()

            // Initialize arrays for storing information from the website
            var cnsImageListSecond = cnsScrapeSecond.getImageList()
            var cnsTitleListSecond = cnsScrapeSecond.getTitleList()
            var cnsUrlListSecond = cnsScrapeSecond.getUrlList()
            var cnsParentUrlListSecond = cnsScrapeSecond.getParentUrlList()

            val cnsListSizeSecond = cnsTitleListSecond.size

            jsonWriterSecond.writeJSONtoFileWithURL(cnsFileNameSecond, cnsListSizeSecond, cnsImageListSecond, cnsTitleListSecond, cnsUrlListSecond, cnsParentUrlListSecond)

            // CnS Page 3 scrape

            // Get the file Location and name where Json File gets stored
            val cnsFileNameFinal = filesDir.path + "/CnSDataFinal.json"
            // call write Json method
            var jsonWriterFinal = JSONReaderWriter(cnsFileNameFinal)

            // JSON for ClubsNSocieties
            var cnsScrapeFinal = CnSScrapeFinal(cnsUrlListSecond)
            cnsScrapeFinal.execute()

            // Initialize arrays for storing information from the website
            var cnsBannerListFinal = cnsScrapeFinal.getBannerImage()
            var cnsLogoListFinal = cnsScrapeFinal.getLogoImage()
            var cnsDescription1ListFinal = cnsScrapeFinal.getDescription1()
            var cnsDescription2ListFinal = cnsScrapeFinal.getDescription2()
            var cnsParentUrlListFinal = cnsScrapeFinal.getParentUrlList()

            val cnsListSizeFinal = cnsBannerListFinal.size

            jsonWriterFinal.writeJSONtoFileCnSFinal(cnsFileNameFinal, cnsListSizeFinal, cnsBannerListFinal, cnsLogoListFinal, cnsDescription1ListFinal, cnsDescription2ListFinal, cnsParentUrlListFinal)
        }
    }
}