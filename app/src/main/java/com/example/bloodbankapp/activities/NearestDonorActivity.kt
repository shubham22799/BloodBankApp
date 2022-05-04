package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.TempValues
import com.example.bloodbankapp.databinding.ActivityNearestDonorBinding

class NearestDonorActivity : AppCompatActivity() {
    lateinit var binding: ActivityNearestDonorBinding
    private var selectedBloodGroup = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNearestDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nearestToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.nearestToolbar.setNavigationOnClickListener { finish() }

        selectedBloodGroup = TempValues.BloodGroup
        binding.selectedBloodGroup.text = selectedBloodGroup
        findNearbyDonor(selectedBloodGroup)

    }

    override fun finish() {
        super.finish()
        TempValues.BloodGroup = ""

    }
    override fun onBackPressed() {
        super.onBackPressed()
        TempValues.BloodGroup = ""
    }

    private fun findNearbyDonor(selectedBloodGroup: String) {

    }
}