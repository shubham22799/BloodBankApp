package com.example.bloodbankapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.TempValues
import com.example.bloodbankapp.databinding.ActivityFindDonorBinding


class FindDonorActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityFindDonorBinding
    private var selectedBloodGroup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.notificationToolbar.setNavigationOnClickListener { finish() }


        binding.aPlus.setOnClickListener(this)
        binding.aMinus.setOnClickListener(this)
        binding.abPlus.setOnClickListener(this)
        binding.abMinus.setOnClickListener(this)
        binding.bPlus.setOnClickListener(this)
        binding.bMinus.setOnClickListener(this)
        binding.oPlus.setOnClickListener(this)
        binding.oMinus.setOnClickListener(this)

        binding.findDonorSubmit.setOnClickListener {
            TempValues.BloodGroup = selectedBloodGroup
            val i = Intent(this, NearestDonorActivity::class.java)
            startActivity(i)
        }
    }

    override fun onClick(view: View) {
        setSelectedButton(view.id)
    }

    private fun setSelectedButton(id: Int) {
        binding.aPlus.isChecked = false
        binding.aMinus.isChecked = false
        binding.abPlus.isChecked = false
        binding.abMinus.isChecked = false
        binding.bPlus.isChecked = false
        binding.bMinus.isChecked = false
        binding.oPlus.isChecked = false
        binding.oMinus.isChecked = false

        val rButton = findViewById<RadioButton>(id)
        rButton.isChecked = true
        selectedBloodGroup = rButton.text.toString()
        Log.v(this.localClassName, selectedBloodGroup)
    }
}