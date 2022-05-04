package com.example.bloodbankapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.transition.Fade
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivitySplashScreenBinding
import com.example.bloodbankapp.utility.SharedPreferencesManager


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    private val SPLASH_DISPLAY_LENGTH: Int = 1800

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_pink)
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            val mainIntent: Intent
            if (SharedPreferencesManager.getUserStatus() == true){
                mainIntent = Intent(this, MainActivity::class.java)
                this.startActivity(mainIntent)
                this.finish()
                return@Runnable
            }
            if(SharedPreferencesManager.getUserStatus() == false){
                mainIntent = Intent(this, QuatesActivity::class.java)
                this.startActivity(mainIntent)
                this.finish()
                return@Runnable
            }

        }, SPLASH_DISPLAY_LENGTH.toLong())

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}