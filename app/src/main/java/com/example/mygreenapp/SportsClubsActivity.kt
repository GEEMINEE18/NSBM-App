package com.example.mygreenapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sportsclub_mainpage.*

class SportsClubsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sportsclub_mainpage)
        //title bar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Sports Clubs"

        val btnAthletic = findViewById<Button>(R.id.athleticBtn)
        val btnBasketball = findViewById<Button>(R.id.basketballBtn)
        val btnBadminton = findViewById<Button>(R.id.badmintonBtn)
        val btnRugby = findViewById<Button>(R.id.rugbyBtn)
        val btnSwimming = findViewById<Button>(R.id.swimmingBtn)
        val btnCricket = findViewById<Button>(R.id.cricketBtn)
        val btnTableTennis = findViewById<Button>(R.id.ttBtn)
        val btnNetball = findViewById<Button>(R.id.netballBtn)
        val btnMartialArts = findViewById<Button>(R.id.martsBtn)
        val btnHockey = findViewById<Button>(R.id.hockeyBtn)
        val btnChess = findViewById<Button>(R.id.chessBtn)
        val btnVolleyball = findViewById<Button>(R.id.volleyballBtn)
        val btnArchery = findViewById<Button>(R.id.archeryBtn)
        val btnFitness = findViewById<Button>(R.id.fitnessBtn)
        val btnSoccer = findViewById<Button>(R.id.soccerBtn)

        btnAthletic.setOnClickListener{
            val intent = Intent (this,activity_athletic_club::class.java)
            startActivity(intent)
        }
        btnBasketball.setOnClickListener{
            val intent = Intent (this,BasketballClubActivity::class.java)
            startActivity(intent)
        }
        btnBadminton.setOnClickListener{
            val intent = Intent (this,BadmintonClubActivity::class.java)
            startActivity(intent)
        }
        btnRugby.setOnClickListener{
            val intent = Intent (this,activity_rugby_club::class.java)
            startActivity(intent)
        }
        /*
        btnSwimming.setOnClickListener{
            val intent = Intent (this,SwimmingClubActivity::class.java)
            startActivity(intent)
        }*/
        btnCricket.setOnClickListener{
            val intent = Intent (this,activity_cricket_club::class.java)
            startActivity(intent)
        }
        /*
        btnTableTennis.setOnClickListener{
            val intent = Intent (this,TableTennisClubActivity::class.java)
            startActivity(intent)
        }*/
        btnNetball.setOnClickListener{
            val intent = Intent (this,activity_netball_club::class.java)
            startActivity(intent)
        }
        btnMartialArts.setOnClickListener{
            val intent = Intent (this,activity_martial_arts_club::class.java)
            startActivity(intent)
        }
        btnHockey.setOnClickListener{
            val intent = Intent (this,activity_hockey_club::class.java)
            startActivity(intent)
        }
        btnChess.setOnClickListener{
            val intent = Intent (this,activity_chess_club::class.java)
            startActivity(intent)
        }
        /*btnVolleyball.setOnClickListener{
            val intent = Intent (this,VolleyballClubActivity::class.java)
            startActivity(intent)
        }*/
        btnArchery.setOnClickListener{
            val intent = Intent (this,activity_archery_club::class.java)
            startActivity(intent)
        }
        btnFitness.setOnClickListener{
            val intent = Intent (this,activity_fitness_club::class.java)
            startActivity(intent)
        }
        btnSoccer.setOnClickListener{
            val intent = Intent (this,activity_soccer_club::class.java)
            startActivity(intent)
        }
    }

}