package com.example.bloodbankapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityFeedbackBinding
    private var state = ""
    private var category = ""
    private var feed = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.feedbackToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.feedbackToolbar.setNavigationOnClickListener { finish() }

        binding.emoji1.setOnClickListener(this)
        binding.emoji2.setOnClickListener(this)
        binding.emoji3.setOnClickListener(this)
        binding.suggestion.setOnClickListener(this)
        binding.compliment.setOnClickListener(this)
        binding.other.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (v?.id) {
            R.id.emoji1 -> {
                setEmoji(id)
            }
            R.id.emoji2 -> {
                setEmoji(id)
            }
            R.id.emoji3 -> {
                setEmoji(id)
            }
            R.id.suggestion -> {
                setUpFeedType(id)
            }
            R.id.compliment -> {
                setUpFeedType(id)
            }
            R.id.other -> {
                setUpFeedType(id)
            }
            R.id.btn_feedback -> { submitFeedback()}

        }
    }

    private fun submitFeedback() {
        feed = binding.etFeedback.text.toString()
        //submit the form
    }

    private fun setUpFeedType(id: Int?) {
        binding.suggestion.isChecked = false
        binding.compliment.isChecked = false
        binding.other.isChecked = false
        when (id) {
            R.id.suggestion -> {
                binding.suggestion.isChecked = true
                category = "suggestion"
            }
            R.id.compliment -> {
                binding.compliment.isChecked = true
                category = "compliment"

            }
            R.id.other -> {
                binding.other.isChecked = true
                category = "other"
            }
        }
    }

    private fun setEmoji(id: Int?) {
        binding.emoji1.setBackgroundResource(R.drawable.ic_extrem_sad_emoji)
        binding.emoji2.setBackgroundResource(R.drawable.ic_neutral_emoji)
        binding.emoji3.setBackgroundResource(R.drawable.ic_happy_emoji)
        when (id) {
            R.id.emoji1 -> {
                binding.emoji1.setBackgroundResource(R.drawable.ic_extrem_sad_emoji_2)
                state = "sad"
            }
            R.id.emoji2 -> {
                binding.emoji2.setBackgroundResource(R.drawable.ic_neutral_emoji2)
                state = "neutral"
            }
            R.id.emoji3 -> {
                binding.emoji3.setBackgroundResource(R.drawable.ic_happy_emoji2)
                state = "happy"
            }
        }
    }
}