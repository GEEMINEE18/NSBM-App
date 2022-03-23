package com.example.mygreenapp

class GetSet() {

    private var imageList = ArrayList<String>()//Creating an empty arraylist
    private var titleList = ArrayList<String>()

    // Setter
    fun setImageList(imgList: ArrayList<String>) {
        imageList = imgList
    }
    // Getter
    fun getImageList(): ArrayList<String> {
        return imageList
    }

    // Setter
    fun setTitleList(tList: ArrayList<String>) {
        titleList = tList
    }
    // Getter
    fun getTitleList(): ArrayList<String> {
        return titleList
    }

}