package com.example.bloodbankapp.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bloodbankapp.activities.AmbulanceActivity
import com.example.bloodbankapp.activities.BloodBankActivity
import com.example.bloodbankapp.activities.MainActivity
import com.example.bloodbankapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val TAG = "HomeFragment"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val appContext = (activity as MainActivity).applicationContext

        var rbSelected = binding.blood.id
        binding.blood.setOnClickListener {
            rbSelected = binding.blood.id
            binding.blood.isChecked = true
            binding.plasma.isChecked = false
            binding.platelet.isChecked = false
        }
        binding.plasma.setOnClickListener {
            binding.blood.isChecked = false
            binding.plasma.isChecked = true
            binding.platelet.isChecked = false
            rbSelected = binding.plasma.id
        }
        binding.platelet.setOnClickListener {
            binding.blood.isChecked = false
            binding.plasma.isChecked = false
            binding.platelet.isChecked = true
            rbSelected = binding.platelet.id
        }

        binding.bloodBank.setOnClickListener {
            startActivity(Intent(appContext, BloodBankActivity::class.java))
        }
        binding.ambulance.setOnClickListener {
            startActivity(Intent(appContext, AmbulanceActivity::class.java))
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

