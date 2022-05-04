package com.example.bloodbankapp.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.DialogCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {
    lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.faqToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.faqToolbar.setNavigationOnClickListener { finish() }

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.faq_popup_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogText =dialog.findViewById<TextView>(R.id.ans_popup)

        binding.q1.setOnClickListener {
            dialogText.text = getString(R.string.ans1)
            dialog.show()
        }

        binding.q2.setOnClickListener {
            dialogText.text = getString(R.string.ans2)
            dialog.show()
        }

        binding.q3.setOnClickListener {
            dialogText.text = getString(R.string.ans3)
            dialog.show()
        }
        binding.q4.setOnClickListener {
            dialogText.text = getString(R.string.ans4)
            dialog.show()
        }
        binding.q5.setOnClickListener {
            dialogText.text = getString(R.string.ans5)
            dialog.show()
        }
        binding.q6.setOnClickListener {
            dialogText.text = getString(R.string.ans6)
            dialog.show()
        }
        binding.q7.setOnClickListener {
            dialogText.text = getString(R.string.ans7)
            dialog.show()
        }





    }
}