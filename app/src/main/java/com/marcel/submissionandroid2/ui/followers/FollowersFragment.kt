package com.marcel.submissionandroid2.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcel.submissionandroid2.databinding.FragmentFollowerBinding
import com.marcel.submissionandroid2.ui.main.MainViewModel

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = requireActivity().intent.getStringExtra("data")

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.isLoading2.observe(this,{
            showLoading(it)
        })

        viewModel.listUserFollowers(username.toString())

        viewModel.listUserFollowers.observe(viewLifecycleOwner,{ list->
            val adapter = FollowersAdapter(list, this)
            binding.rvListFollower.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            binding.rvListFollower.adapter = adapter
        })
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}