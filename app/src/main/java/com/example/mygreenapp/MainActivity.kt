package com.example.mygreenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    //Variable declaration for extended FABs
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    //This is the on/off switch for the extended fab
    private var clicked = false
    //Gives the ability open the drawer by the Toggle button
    //lateinit: will initialize later
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create ReaderWriter object
        var newsWriter = NewsReaderWriter(this)

        newsWriter.checkFiles()

        var yearNow = LocalDateTime.now().year
        var dayNow = LocalDateTime.now().dayOfYear
        var yearLast = newsWriter.loadLastSyncYear()
        var dayLast = newsWriter.loadLastSyncDay()

        // yearNow != yearLast && dayNow != dayLast
        if (yearNow != yearLast && dayNow != dayLast)
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
            var jsonWriter = ReadWriteJSON(cnsFileName)

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
            var jsonWriterSecond = ReadWriteJSON(cnsFileNameSecond)

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
            var jsonWriterFinal = ReadWriteJSON(cnsFileNameFinal)

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


        //eliminating the shadow in bottom nav
        bottomNav.background = null
        //disabling the place holder in the bottom nav
        bottomNav.menu.getItem(2).isEnabled = false

        //For the drawerLayout to work we should include: id 'kotlin-android-extensions' in build.gradle(Module) plugins section
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miItem1 -> startActivity(Intent(this,ProfileActivity::class.java))
                R.id.miItem2 -> Toast.makeText(applicationContext,
                    "Clicked Item 2",Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(applicationContext,
                    "Clicked Item 3",Toast.LENGTH_SHORT).show()
            }
            true
        }

        //Code for bottom nav
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        //Logic code for extended FABs
        fabMain.setOnClickListener {
            onAddMainButtonClicked()
        }
        addEventBtn.setOnClickListener {
            val intent = Intent (this,AddEventActivity::class.java)
            startActivity(intent)
        }
        addPostBtn.setOnClickListener {
            val intent = Intent (this,AddPostActivity::class.java)
            startActivity(intent)
        }
        addMeetingBtn.setOnClickListener {
            val intent = Intent (this,AddMeetingActivity::class.java)
            startActivity(intent)
        }

    }

    //Extra functions for the extended FAB
    private fun onAddMainButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked:Boolean) {
        if (!clicked){
            addEventBtn.visibility = View.VISIBLE
            addPostBtn.visibility = View.VISIBLE
            addMeetingBtn.visibility = View.VISIBLE
        }
        else{
            addEventBtn.visibility = View.INVISIBLE
            addPostBtn.visibility = View.INVISIBLE
            addMeetingBtn.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            addEventBtn.startAnimation(fromBottom)
            addPostBtn.startAnimation(fromBottom)
            addMeetingBtn.startAnimation(fromBottom)
            fabMain.startAnimation(rotateOpen)
        }else{
            addEventBtn.startAnimation(toBottom)
            addPostBtn.startAnimation(toBottom)
            addMeetingBtn.startAnimation(toBottom)
            fabMain.startAnimation(rotateClose)
        }
    }

    //Function to make the extended FABs not clickable when invisible
    private fun setClickable(clicked: Boolean){
        if(!clicked){
            addEventBtn.isClickable = true
            addPostBtn.isClickable = true
            addMeetingBtn.isClickable = true
        }else{
            addEventBtn.isClickable = false
            addPostBtn.isClickable = false
            addMeetingBtn.isClickable = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}