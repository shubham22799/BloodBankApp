package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityHelpdeskBinding

class HelpdeskActivity : AppCompatActivity() {
    lateinit var binding: ActivityHelpdeskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpdeskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.helpDeskToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.helpDeskToolbar.setNavigationOnClickListener { finish() }




    }
}