package com.example.mygreenapp

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File

class ReadWriteJSON(fileName: String) {

    // Initialize arrays for storing information from the website
    private var imagesFirst = ArrayList<String>()
    private var titlesFirst = ArrayList<String>()
    private var urlsFirst = ArrayList<String>()
    var listSize : Int = 0
    private var f = fileName
    private var confirmCount = 0

    fun writeJSONtoFile(fileName: String, listSize: Int, imageList: ArrayList<String>, titleList: ArrayList<String>, urlList: ArrayList<String>) {

        // Define temporary arrays
        var images = ArrayList<String>()
        var titles = ArrayList<String>()
        var urls = ArrayList<String>()

        // Write to array
        for (i in 0 until listSize) {
            images.add(imageList[i])
            titles.add(titleList[i])
            urls.add(urlList[i])
        }

        //Create a Object of Buttons class
        var buttons = Buttons(images, titles, urls, listSize)
        //Create a Object of Gson
        var gson = Gson()
        //Convert the Json object to JsonString
        var jsonString: String = gson.toJson(buttons)
        //Initialize the File Writer and write into file
        val file = File(fileName)
        file.writeText(jsonString)
    }

    fun readJSONData() {
        //Creating a new Gson object to read data
        var gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from bufferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var buttons = gson.fromJson(inputString, Buttons::class.java)
        imagesFirst = buttons.image
        titlesFirst = buttons.title
        urlsFirst = buttons.url
        listSize = buttons.listSize

        confirmCount = 1
    }

    fun readJSONDataWithURL(url: String) {
        //Creating a new Gson object to read data
        var gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from bufferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var buttons = gson.fromJson(inputString, ButtonsWithParent::class.java)
        var clickedUrl = url
        // This thing doesn't work without exception handling.
        try {
            for (i in 0..buttons.listSize) {
                if(clickedUrl == buttons.parentUrl[i]) {
                    imagesFirst.add(buttons.image[i])
                    titlesFirst.add(buttons.title[i])
                    urlsFirst.add(buttons.url[i])
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
        var images = ArrayList<String>()
        var titles = ArrayList<String>()
        var urls = ArrayList<String>()
        var parentUrls = ArrayList<String>()

        // Write to array
        for (i in 0 until listSize) {
            images.add(imageList[i])
            titles.add(titleList[i])
            urls.add(urlList[i])
            parentUrls.add(parentUrlList[i])
        }

        //Create a Object of Buttons class
        var buttons = ButtonsWithParent(images, titles, urls, parentUrls, listSize)
        //Create a Object of Gson
        var gson = Gson()
        //Convert the Json object to JsonString
        var jsonString: String = gson.toJson(buttons)
        //Initialize the File Writer and write into file
        val file = File(fileName)
        file.writeText(jsonString)
    }

    // Getters

    fun getImageList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return imagesFirst
    }

    fun getTitleList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return titlesFirst
    }

    fun getUrlList(): ArrayList<String> {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return urlsFirst
    }

    @JvmName("getListSize1")
    fun getListSize(): Int {
        while (confirmCount == 0)
        {
            //println("Waiting for webscrape")
        }
        return listSize
    }
}