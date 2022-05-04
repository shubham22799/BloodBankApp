package com.example.bloodbankapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbankapp.R
import com.example.bloodbankapp.adapters.BloodBankAdapter
import com.example.bloodbankapp.adapters.BloodBankAdapter.OnItemClickListener
import com.example.bloodbankapp.data.SampleData
import com.example.bloodbankapp.databinding.ActivityBloodBankBinding

class BloodBankActivity : AppCompatActivity() {
    lateinit var binding: ActivityBloodBankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodBankToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.bloodBankToolbar.setNavigationOnClickListener { finish() }
        binding.bloodBankRecyclerView.setHasFixedSize(true)
        binding.bloodBankRecyclerView.layoutManager = LinearLayoutManager(this)

        val data  = SampleData.bloodBankData()

        val bloodBankAdapter = BloodBankAdapter(this, data, object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                val i = Intent(this@BloodBankActivity, DetailBloodBankActivity::class.java)
                i.putExtra("position",position)
                startActivity(i)
        }
        })
        binding.bloodBankRecyclerView.adapter = bloodBankAdapter
    }
}