package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.schedule

class WelcomeActivity : AppCompatActivity() {

    //database
    private lateinit var database: DatabaseReference
    private lateinit var fStore: FirebaseFirestore

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //Delay the welcome activity for 1.5 seconds
        Timer().schedule(1000){
            //init firebaseAuth
            firebaseAuth = FirebaseAuth.getInstance()
            checkUser()
        }

    }

    private fun checkUser() {
        //if user is already logged in go to Main activity
        //get current user
        if (firebaseAuth.currentUser !=null){
            val userId = firebaseAuth.currentUser!!.uid
            val email = firebaseAuth.currentUser!!.email
            //Code to get if userLogin variable true or false from the database
            fStore = FirebaseFirestore.getInstance()
            fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                val document = task.result
                val userCurrent = document.get("host").toString()

                if(userCurrent == "true"){
                    Toast.makeText(this,"You are an Admin", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"You are a user", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this,"Logged in as $email", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,LoadingActivity::class.java)
                intent.putExtra("userCurrent", userCurrent)
                startActivity(intent)
                finish()
            }

        }
        else{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}