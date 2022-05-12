package com.example.mygreenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.net.toUri
import com.example.mygreenapp.databinding.ActivityProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    // Database reference
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()
        getProfilePic()
        getUserData()

        binding.btnEditProfile.setOnClickListener {
            editUserData()
        }

        //handle click, logout
        /*binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }*/
    }

    private fun getProfilePic() {
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference

        var islandRef = storageRef.child("images/$userId")

        val localFile = File.createTempFile("images", ".jpg")

        islandRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            binding.imgProfilePic.setImageURI(localFile.toUri())

        }.addOnFailureListener {
            // Handle any errors
        }
    }

    private fun editUserData() {
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        fStore = FirebaseFirestore.getInstance()
        // Get the document [userId] in the collection "users"
        fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            val doc = task.result
            val name = doc.get("name").toString()
            val stdId = doc.get("stdId").toString()
            val batch = doc.get("batch").toString()
            val email = doc.get("email").toString()

            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("stdId", stdId)
            intent.putExtra("batch", batch)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }

    private fun getUserData() {

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        fStore = FirebaseFirestore.getInstance()
        // Get the document [userId] in the collection "users"
        fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            val doc = task.result
            val name = doc.get("name").toString()
            val stdId = doc.get("stdId").toString()
            val batch = doc.get("batch").toString()
            val email = doc.get("email").toString()

            binding.displayName.text = name
            binding.displayStdId.text = stdId
            binding.displayBatch.text = batch
            binding.displayEmail.text = email
        }
    }


    /*private fun checkUser() {
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
    }*/
}