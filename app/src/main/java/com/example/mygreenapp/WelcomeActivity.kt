package com.example.mygreenapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mygreenapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class WelcomeActivity : AppCompatActivity() {

    //database
    private lateinit var database: DatabaseReference

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //Delay the welcome activity for 1.5 seconds
        Timer().schedule(1500){
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
            database = Firebase.database.reference
            database.child("User").child(userId).child("admin").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                val userCurrent = it.value.toString()

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