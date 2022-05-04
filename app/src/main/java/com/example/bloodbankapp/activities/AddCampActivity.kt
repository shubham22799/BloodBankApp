package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityAddCampBinding

class AddCampActivity : AppCompatActivity() {
    lateinit var  binding: ActivityAddCampBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCampBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addCampToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.addCampToolbar.setNavigationOnClickListener { finish() }

    }
}