package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityReportUsBinding

class ReportUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reportToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.reportToolbar.setNavigationOnClickListener { finish() }


    }
}