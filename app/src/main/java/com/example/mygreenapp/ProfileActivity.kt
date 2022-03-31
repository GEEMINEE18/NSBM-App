package com.example.mygreenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.mygreenapp.databinding.ActivityProfile2Binding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfile2Binding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //Check if user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null){
            //User is logged in, get user info
            val email = firebaseUser.email
            //set to the profile's text view
            binding.lblProfileEmail.text = email
        }
        else{
            //User is not logged in, redirect to Login Activity
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}