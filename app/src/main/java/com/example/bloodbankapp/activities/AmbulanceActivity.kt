package com.example.bloodbankapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbankapp.R
import com.example.bloodbankapp.adapters.AmbulanceAdapter
import com.example.bloodbankapp.adapters.BloodBankAdapter
import com.example.bloodbankapp.data.SampleData
import com.example.bloodbankapp.databinding.ActivityAmbulanceBinding

class AmbulanceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAmbulanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmbulanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ambulanceToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.ambulanceToolbar.setNavigationOnClickListener { finish() }

        binding.ambulanceRecyclerview.setHasFixedSize(true)
        binding.ambulanceRecyclerview.layoutManager = LinearLayoutManager(this)

        val data  = SampleData.ambulanceData()

        val ambulanceAdapter = AmbulanceAdapter(this, data, object : AmbulanceAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val i = Intent(this@AmbulanceActivity, DetailBloodBankActivity::class.java)
                i.putExtra("position",position)
                startActivity(i)
            }
        })
        binding.ambulanceRecyclerview.adapter = ambulanceAdapter

    }
}