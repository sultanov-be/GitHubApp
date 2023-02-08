package com.example.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.githubapp.utils.MarginItemDecoration
import com.example.githubapp.databinding.FragmentMainBinding
import com.example.githubapp.utils.RecyclerAdapter
import com.example.githubapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    lateinit var repoAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        initAdapter()
        observeData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerMain.addItemDecoration(MarginItemDecoration(1, 6, true))
    }

    private fun observeData() {
        viewModel.reposAll.observe(viewLifecycleOwner, Observer {  response ->
            when (response) {
                is Resource.Success -> {
                    loadingBar(false)
                    response.data?.let {
                        repoAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    loadingBar(false)
                    response.message?.let { message ->
                        Log.e("RetrofitAAA", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    loadingBar(true)
                }
            }
        })
    }

    private fun initAdapter(){
        repoAdapter = RecyclerAdapter(requireContext())
        binding.recyclerMain.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }
    }

    private fun loadingBar(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            loadingBar.visibility = VISIBLE
            mainLayout.visibility = INVISIBLE
        } else {
            mainLayout.visibility = VISIBLE
            loadingBar.visibility = INVISIBLE
        }
    }
}