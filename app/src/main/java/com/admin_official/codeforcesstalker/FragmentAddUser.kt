package com.admin_official.codeforcesstalker

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.admin_official.codeforcesstalker.databinding.FragmentAddUserBinding

class FragmentAddUser : Fragment() {

    val viewModel: AppViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddUserBinding.inflate(inflater, container, false)
        binding.adduserEnterText.setOnKeyListener { v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                if(binding.adduserEnterText.text.isNotEmpty()) {
                    viewModel.addHandle(binding.adduserEnterText.text.toString())
                }

                ((context as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(v.windowToken, 0)

                binding.adduserEnterText.clearFocus()
            }
            true
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_adduser_to_activitymain)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = FragmentAddUser()
    }
}