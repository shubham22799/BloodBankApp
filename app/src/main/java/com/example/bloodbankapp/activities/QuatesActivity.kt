package com.example.bloodbankapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityQuatespageBinding

class QuatesActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuatespageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuatespageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}