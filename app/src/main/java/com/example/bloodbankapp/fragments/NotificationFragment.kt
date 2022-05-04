package com.example.bloodbankapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import com.example.bloodbankapp.activities.MainActivity
import com.example.bloodbankapp.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as MainActivity?)?.hideActivityToolBar()


        if(binding.notificationRecycleview.isEmpty()) {
            binding.emptyBox.visibility = View.VISIBLE
            binding.tvEmptyBox.visibility -View.VISIBLE
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity?)?.hideActivityToolBar()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)?.unHideActivityToolBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}