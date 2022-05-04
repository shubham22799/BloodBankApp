package com.example.bloodbankapp.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.SampleData
import com.example.bloodbankapp.databinding.ActivityDetailBloodBankBinding

class DetailBloodBankActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBloodBankBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBloodBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailBloodBankToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.detailBloodBankToolbar.setNavigationOnClickListener { finish() }

        val bloodGroupSpinnerData: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                SampleData.bloodGroupSpinner()
            )
        binding.bloodGroupSpinner.adapter = bloodGroupSpinnerData

        val unitSpinnerData: ArrayAdapter<Int> =
            ArrayAdapter<Int>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                SampleData.unitSpinner()
            )
        binding.unitSpinner.adapter = unitSpinnerData

        if (intent != null) {
            val position = intent.getIntExtra("position", -1)
            if (position != -1) {
                val item = SampleData.bloodBankData()[position]

                binding.detailName.text = item.bankName
                binding.aPlusPb.progress = item.aPlus
                binding.aPlusUnit.text = item.aPlus.toString()
                binding.aMinusPb.progress = item.aMinus
                binding.aMinusUnit.text = item.aMinus.toString()
                binding.bPlusPb.progress = item.bPlus
                binding.bPlusUnit.text = item.bPlus.toString()
                binding.bMinusPb.progress = item.bMinus
                binding.bMinusUnit.text = item.bMinus.toString()
                binding.oPlusPb.progress = item.oPlus
                binding.oPlusUnit.text = item.oPlus.toString()
                binding.oMinusPb.progress = item.oMinus
                binding.oMinusUnit.text = item.oMinus.toString()
                binding.abPlusPb.progress = item.abPlus
                binding.abPlusUnit.text = item.abPlus.toString()
                binding.abMinusPb.progress = item.abMinus
                binding.abMinusUnit.text = item.abMinus.toString()

            } else {
                Toast.makeText(this, "position is -1", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "intent is null", Toast.LENGTH_SHORT).show()
        }
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.confirm_order_layout)
        val confirmOrder = dialog.findViewById<TextView>(R.id.confirm_order)
        confirmOrder.setOnClickListener {
            Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_SHORT).show()
            dialog.hide()
            finish()
        }
        binding.order.setOnClickListener {
            dialog.show()
        }
    }
}