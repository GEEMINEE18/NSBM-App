package com.example.mygreenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Variable declaration for extended FABs
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    //This is the on/off switch for the extended fab
    private var clicked = false
    //Gives the ability open the drawer by the Toggle button
    private lateinit var toggle: ActionBarDrawerToggle

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //eliminating the shadow in bottom nav
        bottomNav.background = null
        //disabling the place holder in the bottom nav
        bottomNav.menu.getItem(2).isEnabled = false

        //Admin variable
        val host = intent.getStringExtra("host")
        val userCurrent = intent.getStringExtra("userCurrent")

        if (host == "false" || userCurrent == "false"){
            fabMain.visibility = View.INVISIBLE
        }
        else{
            fabMain.visibility = View.VISIBLE
        }

        //For the drawerLayout to work we should include: id 'kotlin-android-extensions' in build.gradle(Module) plugins section
        toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miItem1 -> startActivity(Intent(this,ProfileActivity::class.java))
                R.id.miItem2 -> {
                    startActivity(Intent(this,SettingsActivity::class.java))
                }
                R.id.miItem3 -> {
                    firebaseAuth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
            }
            true
        }

        //Code for bottom nav
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        //Logic code for extended FABs
        fabMain.setOnClickListener {
            onAddMainButtonClicked()
        }
        addEventBtn.setOnClickListener {
            val intent = Intent (this,AddEventActivity::class.java)
            startActivity(intent)
        }
        addPostBtn.setOnClickListener {
            val intent = Intent (this,AddPostActivity::class.java)
            startActivity(intent)
        }
        addMeetingBtn.setOnClickListener {
            val intent = Intent (this,AddMeetingActivity::class.java)
            startActivity(intent)
        }

    }

    //Extra functions for the extended FAB
    private fun onAddMainButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked:Boolean) {
        if (!clicked){
            addEventBtn.visibility = View.VISIBLE
            addPostBtn.visibility = View.VISIBLE
            addMeetingBtn.visibility = View.VISIBLE
        }
        else{
            addEventBtn.visibility = View.INVISIBLE
            addPostBtn.visibility = View.INVISIBLE
            addMeetingBtn.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            addEventBtn.startAnimation(fromBottom)
            addPostBtn.startAnimation(fromBottom)
            addMeetingBtn.startAnimation(fromBottom)
            fabMain.startAnimation(rotateOpen)
        }else{
            addEventBtn.startAnimation(toBottom)
            addPostBtn.startAnimation(toBottom)
            addMeetingBtn.startAnimation(toBottom)
            fabMain.startAnimation(rotateClose)
        }
    }

    //Function to make the extended FABs not clickable when invisible
    private fun setClickable(clicked: Boolean){
        if(!clicked){
            addEventBtn.isClickable = true
            addPostBtn.isClickable = true
            addMeetingBtn.isClickable = true
        }else{
            addEventBtn.isClickable = false
            addPostBtn.isClickable = false
            addMeetingBtn.isClickable = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}