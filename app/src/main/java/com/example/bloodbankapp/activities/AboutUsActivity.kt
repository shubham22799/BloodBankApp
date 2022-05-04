package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityAboutUsBinding
import com.example.bloodbankapp.setColors

class AboutUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textview1.setColors(getString(R.string.savlife), getString(R.string.savlife_is_), R.color.pink, R.color.black)
        binding.textview2.setColors(getString(R.string.savlife), getString(R.string.mission_is), R.color.pink, R.color.black)

        binding.aboutUsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.aboutUsToolbar.setNavigationOnClickListener { finish() }

    }
}