package com.example.mygreenapp

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_page.*
import org.jsoup.Jsoup

class NewsPageActivity: AppCompatActivity() {
    // Declarations for the recycler view
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_page)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "News"



        // Initialize arrays for storing information from the website
        var imgList = ArrayList<String>()
        var titleList = ArrayList<String>()
        var descriptionList = ArrayList<String>()

        // Initialize all 9 cards for display in XML file

        // Card 1
        var webImg1 = findViewById<ImageView>(R.id.imgWeb1)
        var webTitle1 = findViewById<TextView>(R.id.txtWebTitle1)
        var webDescription1 = findViewById<TextView>(R.id.txtWebDescription1)
        // Card 2
        var webImg2 = findViewById<ImageView>(R.id.imgWeb2)
        var webTitle2 = findViewById<TextView>(R.id.txtWebTitle2)
        var webDescription2 = findViewById<TextView>(R.id.txtWebDescription2)
        // Card 3
        var webImg3 = findViewById<ImageView>(R.id.imgWeb3)
        var webTitle3 = findViewById<TextView>(R.id.txtWebTitle3)
        var webDescription3 = findViewById<TextView>(R.id.txtWebDescription3)
        // Card 4
        var webImg4 = findViewById<ImageView>(R.id.imgWeb4)
        var webTitle4 = findViewById<TextView>(R.id.txtWebTitle4)
        var webDescription4 = findViewById<TextView>(R.id.txtWebDescription4)
        // Card 5
        var webImg5 = findViewById<ImageView>(R.id.imgWeb5)
        var webTitle5 = findViewById<TextView>(R.id.txtWebTitle5)
        var webDescription5 = findViewById<TextView>(R.id.txtWebDescription5)
        // Card 6
        var webImg6 = findViewById<ImageView>(R.id.imgWeb6)
        var webTitle6 = findViewById<TextView>(R.id.txtWebTitle6)
        var webDescription6 = findViewById<TextView>(R.id.txtWebDescription6)
        // Card 7
        var webImg7 = findViewById<ImageView>(R.id.imgWeb7)
        var webTitle7 = findViewById<TextView>(R.id.txtWebTitle7)
        var webDescription7 = findViewById<TextView>(R.id.txtWebDescription7)
        // Card 8
        var webImg8 = findViewById<ImageView>(R.id.imgWeb8)
        var webTitle8 = findViewById<TextView>(R.id.txtWebTitle8)
        var webDescription8 = findViewById<TextView>(R.id.txtWebDescription8)
        // Card 9
        var webImg9 = findViewById<ImageView>(R.id.imgWeb9)
        var webTitle9 = findViewById<TextView>(R.id.txtWebTitle9)
        var webDescription9 = findViewById<TextView>(R.id.txtWebDescription9)

        // This WebScrape class runs in the background as an Asynchronous task, without affecting the main task
        class WebScrape() : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? {
                var doc = Jsoup.connect("https://www.nsbm.ac.lk/news/").get()
                var allInfo = doc.getElementsByClass("col-sm-6 col-lg-4 blog-isotope-item kl-blog-column")
                //println(allInfo)

                // This loop will look for all the elements in the variable allInfo
                for (i in allInfo)
                {
                    var webImg = i.getElementsByTag("img").attr("src")
                    var webTitle = i.getElementsByClass("itemHeader kl-blog-item-header").text()
                    var webDescription = i.getElementsByClass("itemIntroText kl-blog-item-content").text()
                    // the println function is used only for checking if the values are retrieved or not
                    println(webImg)
                    // Add the scraped data into the arrays
                    imgList.add(webImg)
                    titleList.add(webTitle)
                    descriptionList.add(webDescription)
                }

                // Glide is used to show an image taken from the web source
                // The code below displays everything taken from the web source on the activity
                runOnUiThread {
                    Glide.with(this@NewsPageActivity).load(imgList[0]).into(webImg1)
                    webTitle1.text = titleList[0]
                    webDescription1.text = descriptionList[0]
                    Glide.with(this@NewsPageActivity).load(imgList[1]).into(webImg2)
                    webTitle2.text = titleList[1]
                    webDescription2.text = descriptionList[1]
                    Glide.with(this@NewsPageActivity).load(imgList[2]).into(webImg3)
                    webTitle3.text = titleList[2]
                    webDescription3.text = descriptionList[2]
                    Glide.with(this@NewsPageActivity).load(imgList[3]).into(webImg4)
                    webTitle4.text = titleList[3]
                    webDescription4.text = descriptionList[3]
                    Glide.with(this@NewsPageActivity).load(imgList[4]).into(webImg5)
                    webTitle5.text = titleList[4]
                    webDescription5.text = descriptionList[4]
                    Glide.with(this@NewsPageActivity).load(imgList[5]).into(webImg6)
                    webTitle6.text = titleList[5]
                    webDescription6.text = descriptionList[5]
                    Glide.with(this@NewsPageActivity).load(imgList[6]).into(webImg7)
                    webTitle7.text = titleList[6]
                    webDescription7.text = descriptionList[6]
                    Glide.with(this@NewsPageActivity).load(imgList[7]).into(webImg8)
                    webTitle8.text = titleList[7]
                    webDescription8.text = descriptionList[7]
                    Glide.with(this@NewsPageActivity).load(imgList[8]).into(webImg9)
                    webTitle9.text = titleList[8]
                    webDescription9.text = descriptionList[8]
                }

                // The following code block was supposed to set the array for the recycler view

                /*var getsetobj = GetSet()

                // Setter
                getsetobj.setImageList(imgList)
                getsetobj.setTitleList(titleList)*/

                //

                return null
            }

            override fun onPreExecute() {
                super.onPreExecute()
                // ...
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                // ...
            }
        }

        val task = WebScrape()
        task.execute()



        // The following code block was supposed to manage the recycler view for the web scraping, didn't work unfortunately

        /*layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter*/

        //
    }
}