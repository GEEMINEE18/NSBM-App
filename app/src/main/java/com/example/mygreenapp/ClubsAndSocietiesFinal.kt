package com.example.mygreenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ClubsAndSocietiesFinal : AppCompatActivity() {

    // Firebase declarations
    private lateinit var fStore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clubs_and_societies_final)

        val backButton = findViewById<Button>(R.id.btnBack)
        val followButton = findViewById<Button>(R.id.btnFollow)

        backButton.setOnClickListener{
            finish()
        }

        // Get value from ClubAndSocietiesActivity
        val hiddenUrl = intent.getStringExtra("url")
        val pageTitle = intent.getStringExtra("title")

        // Get the file Location and name where Json File gets stored
        val fileName = filesDir.path + "/CnSDataFinal.json"
        // Read the written Json File
        var reader = JSONReaderWriter(fileName)
        // Send the url of the clicked parent button to the JSONReaderWriter file
        if (hiddenUrl != null) {
            reader.readJSONDataCnSFinal(hiddenUrl)
        }

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // Current firebase user
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        // Change text on follow button when user enters the activity
        fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            val document = task.result
            val followingList = document.get("following") as ArrayList<*>

            // check if the user follows the club or not
            var found = false
            var i = 0

            // The loop will run until it finds the club name or it reaches the array size
            try {
                while (!found || i != followingList.size) {
                    if (followingList[i] == pageTitle) {
                        found = true
                    }
                    i++
                }
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }


            if (found) {
                followButton.text = "Unfollow"
            }
            else {
                followButton.text = "Follow"
            }

            followButton.visibility = VISIBLE
        }

        followButton.setOnClickListener {

            val userRef = fStore.collection("users").document(userId)

            // Change text on follow button during the existing session
            if (followButton.text == "Follow") {
                // This is necessary to update an array
                userRef.update("following", FieldValue.arrayUnion(pageTitle))
                Toast.makeText(this@ClubsAndSocietiesFinal, "Followed successfully!", Toast.LENGTH_SHORT).show()

                followButton.text = "Unfollow"
            }
            else if (followButton.text == "Unfollow") {
                userRef.update("following", FieldValue.arrayRemove(pageTitle))
                Toast.makeText(this@ClubsAndSocietiesFinal, "Unfollowed successfully!", Toast.LENGTH_SHORT).show()

                followButton.text = "Follow"
            }
        }

        var bannerImage = reader.getBannerImage()
        var logoImage = reader.getLogoImage()
        var firstDescription = reader.getDescription1()
        var secondDescription = reader.getDescription2()

        var imgBanner = findViewById<ImageView>(R.id.imgCSBanner)
        var imgLogo = findViewById<ImageView>(R.id.imgCSLogo)

        Glide.with(this@ClubsAndSocietiesFinal).load(bannerImage).into(imgBanner)
        Glide.with(this@ClubsAndSocietiesFinal).load(logoImage).into(imgLogo)
        findViewById<TextView>(R.id.txtCSTitle).text = pageTitle
        findViewById<TextView>(R.id.txtDescriptionContent1).text = firstDescription
        findViewById<TextView>(R.id.txtDescriptionContent2).text = secondDescription
    }
}