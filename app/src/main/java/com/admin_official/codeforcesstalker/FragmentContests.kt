package com.admin_official.codeforcesstalker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.admin_official.codeforcesstalker.databinding.FragmentContestsBinding

class FragmentContests : Fragment() {

    lateinit var binding: FragmentContestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContestsBinding.inflate(inflater, null, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentContests()
    }
}