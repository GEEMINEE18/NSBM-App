package com.example.mygreenapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mygreenapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding

    private lateinit var database: DatabaseReference

    //ActionBar
    private lateinit var actionBar: ActionBar

    //progressDialog
    private lateinit var progressDialog:ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, open register activity
        binding.lblNoAccount.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.btnLogin.setOnClickListener {

            validateData()

        }

    }

    private fun validateData() {
        //get data
        email = binding.txtEmail.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        //Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.txtEmail.error = "Invalid email Format"

        }
        else if(TextUtils.isEmpty(password)){
            //No password entered
            binding.txtPassword.error = "Please enter password"
        }
        else{
            //login if data is validated
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {

        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login Successful
                checkIfAdmin()
            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in go to Main activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser !=null){
            //user is already logged in
            val userCurrent = "false"
            val intent = Intent(this@LoginActivity,LoadingActivity::class.java)
            intent.putExtra("userCurrent", userCurrent)
            startActivity(intent)
        }
    }

    private fun checkIfAdmin(){

        progressDialog.dismiss()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email
        val userId = firebaseUser.uid

        //Code to get if userLogin variable true or false from the database
        database = Firebase.database.reference
        database.child("User").child(userId).child("admin").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val userLogin = it.value.toString()

            if(userLogin == "true"){
                Toast.makeText(this,"You are an Admin",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"You are a user",Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()

            val intent = Intent(this@LoginActivity,LoadingActivity::class.java)
            intent.putExtra("userLogin", userLogin)
            startActivity(intent)

        }
    }
}