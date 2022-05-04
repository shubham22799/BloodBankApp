package com.example.bloodbankapp.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.bloodbankapp.R
import com.example.bloodbankapp.data.TempValues
import com.example.bloodbankapp.databinding.ActivityRequestBloodBinding
import kotlinx.coroutines.android.awaitFrame

class RequestBloodActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityRequestBloodBinding
    private var selectedBloodGroup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reqBloodToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.reqBloodToolbar.setNavigationOnClickListener { finish() }
        binding.aPlus.setOnClickListener(this)
        binding.aMinus.setOnClickListener(this)
        binding.abPlus.setOnClickListener(this)
        binding.abMinus.setOnClickListener(this)
        binding.bPlus.setOnClickListener(this)
        binding.bMinus.setOnClickListener(this)
        binding.oPlus.setOnClickListener(this)
        binding.oMinus.setOnClickListener(this)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.submit_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.reqBloodSubmit.setOnClickListener {
            if (submitRequest()) {
                dialog.show()
                val r = Runnable {
                    finish()
                }
                val h = Handler()
                h.postDelayed(r, 1000)
            } else {
                Toast.makeText(this, "Failed to submit request!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitRequest(): Boolean {
        return true
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
    }
}