package com.example.mygreenapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mygreenapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private  lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating new account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin register
        binding.btnRegister.setOnClickListener {

            //validate data
            validateData()
        }
    }

    private fun validateData() {

        email = binding.txtEmail.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        //Validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.txtEmail.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //Password is not entered
            binding.txtPassword.error = "Please enter password"
        }
        else if(password.length<6){
            //password is short
            binding.txtPassword.error = "Password must be at least more than 6 characters"
        }
        else{
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {

        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Successfully registered
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val userId = firebaseUser.uid

                val name = binding.txtName.text.toString()
                val stdId = binding.txtStdId.text.toString()
                val batch = binding.txtBatch.text.toString()

                database = FirebaseDatabase.getInstance().getReference("User")
                val register = Register(name, stdId, batch, email, isAdmin = false)
                database.child(userId).setValue(register)

                Toast.makeText(this,"Registered with $email",Toast.LENGTH_SHORT).show()

                val userLogin = "false"

                val intent = Intent(this@RegisterActivity,LoadingActivity::class.java)
                intent.putExtra("userLogin", userLogin)
                startActivity(intent)
            }
            .addOnFailureListener {e->
                //Failed to register
                Toast.makeText(this,"Registration failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        //Go back to previous activity
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}