package com.marcel.submissionandroid2.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcel.submissionandroid2.databinding.FragmentFollowingBinding
import com.marcel.submissionandroid2.ui.main.MainViewModel


class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var username = requireActivity().intent.getStringExtra("data")

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.listUserFollowing(username.toString())
        viewModel.isLoading.observe(this,{
            showLoading(it)
        })

        viewModel.listUserFollowing.observe(viewLifecycleOwner,{ list ->
            val adapter = FollowingAdapter(list,this)
            binding.rvListFollowing.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvListFollowing.adapter = adapter
        })

    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}