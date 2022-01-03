package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.admin_official.codeforcesstalker.databinding.FragmentUserDetailBinding
import com.admin_official.codeforcesstalker.databinding.FragmentUserInfoBinding
import com.admin_official.codeforcesstalker.objects.Handle

const val HANDLE_CLICKED = "handleClicked"

private const val TAG = "De_FragmentUserDetail"

class FragmentUserDetail : Fragment() {

    lateinit var binding: FragmentUserDetailBinding
    lateinit var handle: Handle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply { handle = (getParcelable(HANDLE_CLICKED)!!) }
        Log.d(TAG, "onCreate: $handle")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater)

        binding.userDetailName
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = FragmentUserDetail()
    }
}