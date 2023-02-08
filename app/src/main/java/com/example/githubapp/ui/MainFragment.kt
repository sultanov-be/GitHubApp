package com.example.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.githubapp.data.model.ReposList
import com.example.githubapp.utils.MarginItemDecoration
import com.example.githubapp.databinding.FragmentMainBinding
import com.example.githubapp.utils.RecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel.getInfo()
        lifecycleScopeObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerMain.addItemDecoration(MarginItemDecoration(1, 6, true))
    }

    private fun lifecycleScopeObserver() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.RepoEvent.Success -> {
                        loadingBar(false)
                        loadingBar.isVisible = false
                        recyclerMain.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
                        recyclerMain.adapter = RecyclerAdapter(event.result)
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    }
                    is MainViewModel.RepoEvent.Failure -> {
                        loadingBar.isVisible = false
                        Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show()
                    }
                    is MainViewModel.RepoEvent.Loading -> {
                        loadingBar(true)
                        Toast.makeText(requireContext(), "load", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
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