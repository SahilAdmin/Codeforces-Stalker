package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.admin_official.codeforcesstalker.databinding.FragmentUserDetailBinding
import com.admin_official.codeforcesstalker.databinding.FragmentUserInfoBinding
import com.admin_official.codeforcesstalker.logic.AppViewModel
import com.admin_official.codeforcesstalker.objects.Handle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

const val HANDLE_CLICKED = "handleClicked"

private const val TAG = "De_FragmentUserDetail"

class FragmentUserDetail : Fragment() {

    lateinit var binding: FragmentUserDetailBinding
    lateinit var handle: Handle
    val viewModel: AppViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater)

        arguments?.apply { handle = (getParcelable(HANDLE_CLICKED)!!) }

        viewModel.userStatus.observe(viewLifecycleOwner, {
            if(it != null) {
                this.handle.problems = it
                loadPage(this.handle)
//                viewModel.nullUserStatus()
            }
        })

        return binding.root
    }

    fun loadPage(handle: Handle) {
        handle.calc()

        Glide.with(binding.root.context)
            .load(handle.dp)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.userDetailDp)

        binding.userDetailName.text = handle.username
        binding.userDetailRank.text = handle.rank
        binding.userDetailRating.text = handle.rating.toString()

        val sb = StringBuilder()
        sb.append("Max Rank: ").append(handle.maxRank).append("\n")
        sb.append("Max Rating: ").append(handle.maxRating).append("\n\n")
        sb.append("Today: \n")
        sb.append("Submissions: ").append(handle.submissionsToday).append("\n")
        sb.append("Accepted: ").append(handle.acceptedToday).append("\n")
        sb.append("Max Rating: ").append(handle.ratingMax[0]).append("\n")
        sb.append("Average Rating: ").append(handle.ratingToday[2]).append("\n\n")
        sb.append("Past Ten Days: \n")
        sb.append("Submissions: ").append(handle.submissions10Days).append("\n")
        sb.append("Accepted: ").append(handle.accepted10Days).append("\n")
        sb.append("Max Rating: ").append(handle.ratingMax[1]).append("\n")
        sb.append("Average Rating: ").append(handle.rating10Days[2]).append("\n\n")
        sb.append("Last 100 submissions: ").append("\n")
        sb.append("Accepted: ").append(handle.accepted).append("\n")
        sb.append("Max Rating: ").append(handle.ratingMax[2]).append("\n")
        sb.append("Average Rating: ").append(handle.ratingTotal[2]).append("\n")

        binding.userDetailDescription.text = sb.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = FragmentUserDetail()
    }
}