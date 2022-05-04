package com.example.bloodbankapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodbankapp.activities.MainActivity
import com.example.bloodbankapp.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root = binding.root
        (activity as MainActivity?)?.hideActivityToolBar()
//

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

}