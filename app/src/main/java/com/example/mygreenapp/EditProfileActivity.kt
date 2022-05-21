package com.example.mygreenapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mygreenapp.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityEditProfileBinding
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    // Database reference
    private lateinit var fStore: FirebaseFirestore

    private lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        val name = intent.getStringExtra("name")
        val stdId = intent.getStringExtra("stdId")
        val batch = intent.getStringExtra("batch")
        val email = intent.getStringExtra("email")

        binding.txtName.setText(name)
        binding.txtStdID.setText(stdId)
        binding.txtBatch.setText(batch)
        binding.txtEmail.setText(email)

        binding.editProfilePic.setOnClickListener {
            selectImage()
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {

        // Setting the uploaded time as part of image file name
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        // Filename for the image uploaded by the user

        // Reference to firebase storage
        val storageReference = FirebaseStorage.getInstance().getReference("image/$userId")

        // Uploading file to firebase cloud storage
        storageReference.putFile(imageUri).
        addOnSuccessListener {
            binding.editProfilePic.setImageURI(null)
            Toast.makeText(this@EditProfileActivity, "Successfully uploaded image!", Toast.LENGTH_SHORT).show()
            //if (progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener{
            //if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@EditProfileActivity, "Upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.editProfilePic.setImageURI(imageUri)
        }
    }
}