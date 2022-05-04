package com.example.bloodbankapp.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbankapp.R
import com.example.bloodbankapp.activities.MainActivity
import com.example.bloodbankapp.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        val root = binding.root
        val appContext =  (activity as MainActivity)
        appContext.hideActivityToolBar()

        val dialog = Dialog(appContext)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.wholeBloodTv.setOnClickListener {
            dialog.setContentView(R.layout.whole_blood_popup)
            dialog.show()
        }
        binding.plateletTv.setOnClickListener {
            dialog.setContentView(R.layout.platelet_popup)
            dialog.show()
        }
        binding.plasmaTv.setOnClickListener {
            dialog.setContentView(R.layout.plasma_popup)
            dialog.show()
        }
        binding.donationMattersTv.setOnClickListener {
            dialog.setContentView(R.layout.donation_matters_popup)
            dialog.show()
        }

        return root
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)?.unHideActivityToolBar()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity?)?.hideActivityToolBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}