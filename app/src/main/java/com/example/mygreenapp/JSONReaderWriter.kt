package com.example.mygreenapp

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File

class JSONReaderWriter(fileName: String) {

    // Initialize arrays for storing information from the website
    private var images = ArrayList<String>()
    private var titles = ArrayList<String>()
    private var urls = ArrayList<String>()
    // CnS Final page specific
    private var banner = String()
    private var logo = String()
    private var description1 = String()
    private var description2 = String()
    //
    private var listSize : Int = 0
    private var f = fileName
    private var confirmCount = 0

    fun writeJSONtoFile(fileName: String, listSize: Int, imageList: ArrayList<String>, titleList: ArrayList<String>, urlList: ArrayList<String>) {

        // Define temporary arrays
        val images = ArrayList<String>()
        val titles = ArrayList<String>()
        val urls = ArrayList<String>()

        // Write to array
        for (i in 0 until listSize) {
            images.add(imageList[i])
            titles.add(titleList[i])
            urls.add(urlList[i])
        }

        //Create a Object of Buttons class
        val buttons = CnSButtonsFirst(images, titles, urls, listSize)
        //Create a Object of Gson
        val gson = Gson()
        //Convert the Json object to JsonString
        val jsonString: String = gson.toJson(buttons)
        //Initialize the File Writer and write into file
        val file = File(fileName)
        file.writeText(jsonString)
    }

    fun readJSONData() {
        //Creating a new Gson object to read data
        val gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from bufferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        val buttons = gson.fromJson(inputString, CnSButtonsFirst::class.java)
        images = buttons.image
        titles = buttons.title
        urls = buttons.url
        listSize = buttons.listSize

        confirmCount = 1
    }

    fun readJSONDataWithURL(url: String) {
        //Creating a new Gson object to read data
        val gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from bufferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        val buttons = gson.fromJson(inputString, CnSButtonsSecond::class.java)
        // This thing doesn't work without exception handling.
        try {
            for (i in 0..buttons.listSize) {
                if (url == buttons.parentUrl[i]) {
                    images.add(buttons.image[i])
                    titles.add(buttons.title[i])
                    urls.add(buttons.url[i])
                    listSize++
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        confirmCount = 1
    }

    fun writeJSONtoFileWithURL(fileName: String, listSize: Int, imageList: ArrayList<String>, titleList: ArrayList<String>, urlList: ArrayList<String>, parentUrlList: ArrayList<String>) {

        // Define temporary arrays
        val images = ArrayList<String>()
        val titles = ArrayList<String>()
        val urls = ArrayList<String>()
        val parentUrls = ArrayList<String>()

        // Write to array
        for (i in 0 until listSize) {
            images.add(imageList[i])
            titles.add(titleList[i])
            urls.add(urlList[i])
            parentUrls.add(parentUrlList[i])
        }

        //Create a Object of Buttons class
        val buttons = CnSButtonsSecond(images, titles, urls, parentUrls, listSize)
        //Create a Object of Gson
        val gson = Gson()
        //Convert the Json object to JsonString
        val jsonString: String = gson.toJson(buttons)
        //Initialize the File Writer and write into file
        val file = File(fileName)
        file.writeText(jsonString)
    }

    fun readJSONDataCnSFinal(url: String) {
        //Creating a new Gson object to read data
        val gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from bufferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        val buttons = gson.fromJson(inputString, CnSButtonsFinal::class.java)
        // This thing doesn't work without exception handling.
        try {
            for (i in 0..buttons.listSize) {
                if (url == buttons.parentUrl[i]) {
                    banner = buttons.banner[i]
                    logo = buttons.logo[i]
                    description1 = buttons.description1[i]
                    description2 = buttons.description2[i]
                    listSize++
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        confirmCount = 1
    }

    fun writeJSONtoFileCnSFinal(fileName: String, listSize: Int, bannerList: ArrayList<String>, logoList: ArrayList<String>, description1List: ArrayList<String>, description2List: ArrayList<String>, parentUrlList: ArrayList<String>) {

        // Define temporary arrays
        val banners = ArrayList<String>()
        val logos = ArrayList<String>()
        val description1 = ArrayList<String>()
        val description2 = ArrayList<String>()
        val parentUrls = ArrayList<String>()

        // Write to array
        for (i in 0 until listSize) {
            banners.add(bannerList[i])
            logos.add(logoList[i])
            description1.add(description1List[i])
            description2.add(description2List[i])
            parentUrls.add(parentUrlList[i])
        }

        //Create a Object of Buttons class
        val buttons = CnSButtonsFinal(banners, logos, description1, description2, parentUrls, listSize)
        //Create a Object of Gson
        val gson = Gson()
        //Convert the Json object to JsonString
        val jsonString: String = gson.toJson(buttons)
        //Initialize the File Writer and write into file
        val file = File(fileName)
        file.writeText(jsonString)
    }

    // Getters

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return images
    }

    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return titles
    }

    fun getUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return urls
    }

    @JvmName("getListSize1")
    fun getListSize(): Int {
        while (confirmCount == 0)
        {
            //println("Waiting for web-scrape")
        }
        return listSize
    }

    // Cns Final specific
    fun getBannerImage(): String {
        while (confirmCount == 0)
        {
            // wait
        }
        return banner
    }

    fun getLogoImage(): String {
        while (confirmCount == 0)
        {
            // wait
        }
        return logo
    }

    fun getDescription1(): String {
        while (confirmCount == 0)
        {
            // wait
        }
        return description1
    }

    fun getDescription2(): String {
        while (confirmCount == 0)
        {
            // wait
        }
        return description2
    }
}