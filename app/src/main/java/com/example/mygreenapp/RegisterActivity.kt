@file:Suppress("DEPRECATION")

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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var fStore: FirebaseFirestore

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

        binding.boxName.helperText = ""
        binding.boxStdId.helperText = ""
        binding.boxBatch.helperText = ""
        binding.boxEmail.helperText = ""
        binding.boxPassword.helperText = ""

        nameFocusListener()
        stdIDFocusListener()
        batchFocusListener()
        emailFocusListener()
        passwordFocusListener()

        //handle click, begin register
        binding.btnRegister.setOnClickListener {

            //validate data
            validateData()
        }
    }

    private fun validateData() {
        binding.boxName.helperText = validName()
        binding.boxStdId.helperText = validStdID()
        binding.boxBatch.helperText = validBatch()
        binding.boxEmail.helperText = validEmail()
        binding.boxPassword.helperText = validPassword()

        val validName = binding.boxName.helperText == null
        val validStdID = binding.boxStdId.helperText == null
        val validBatch = binding.boxBatch.helperText == null
        val validEmail = binding.boxEmail.helperText == null
        val validPassword = binding.boxPassword.helperText == null

        if (validName && validStdID && validBatch && validEmail && validPassword) {
            firebaseRegister()
        }
        else {
            Toast.makeText(this, "Fill the Required fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun nameFocusListener()
    {
        binding.txtName.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.boxName.helperText = validName()
            }
        }
    }

    private fun validName(): String?
    {
        val nameText = binding.txtName.text.toString()
        if(TextUtils.isEmpty(nameText))
        {
            return "Required"
        }
        return null
    }

    private fun stdIDFocusListener()
    {
        binding.txtStdId.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.boxStdId.helperText = validStdID()
            }
        }
    }

    private fun validStdID(): String?
    {
        val stdIDText = binding.txtStdId.text.toString()
        if(TextUtils.isEmpty(stdIDText))
        {
            return "Required"
        }
        return null
    }

    private fun batchFocusListener()
    {
        binding.txtBatch.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.boxBatch.helperText = validBatch()
            }
        }
    }

    private fun validBatch(): String?
    {
        val batchText = binding.txtBatch.text.toString()
        if(TextUtils.isEmpty(batchText))
        {
            return "Required"
        }
        return null
    }

    private fun emailFocusListener()
    {
        binding.txtEmail.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.boxEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String?
    {
        email = binding.txtEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.txtPassword.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.boxPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        password = binding.txtPassword.text.toString()
        if(password.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!password.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!password.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!password.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
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
                val following = ArrayList<String>()
                following.add("")

                fStore = FirebaseFirestore.getInstance()
                val register = Register(name, stdId, batch, email, headOf = "", isHost = false, following)
                fStore.collection("users").document(userId).set(register)

                Toast.makeText(this, "Registered with $email", Toast.LENGTH_SHORT).show()

                val host = "false"

                val intent = Intent(this@RegisterActivity, LoadingActivity::class.java)
                intent.putExtra("host", host)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                //Failed to register
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        //Go back to previous activity
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}