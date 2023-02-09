package com.example.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.model.ReposList
import com.example.githubapp.utils.MarginItemDecoration
import com.example.githubapp.databinding.FragmentMainBinding
import com.example.githubapp.utils.RecyclerAdapter
import com.example.githubapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var repoAdapter: RecyclerAdapter
    private var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        repoAdapter = RecyclerAdapter(requireContext())

        initAdapter()
        initSearch()
        observeData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeData() {
        viewModel.reposAll.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    loadingBar(false)
                    response.data?.let {
                        repoAdapter.differ.submitList(it)
                    }
                    setViews(response.data!!)
                }
                is Resource.Error -> {
                    showError(true)
                    response.message?.let { message ->
                        Log.e("RetrofitAAA", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    loadingBar(true)
                }
            }
        }
    }

    private fun initSearch() = with(binding) {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (queryIsAcceptable(query)) {
                        viewModel.getRepos(query!!)
                        loadingBar(true)
                    } else {
                        showError(false)
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
    }

    private fun queryIsAcceptable(q: String?): Boolean {
        return if (q == null || q == "") false
        else !('.' in q || ',' in q || '!' in q || '?' in q || '(' in q || ')' in q)
    }

    private fun setViews(item: ReposList) = with(binding) {
        try {
            searchView.clearFocus()
            userNickname.text = item[0].owner.login

            Glide.with(requireContext()).load(item[0].owner.avatar_url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.loading_img).into(binding.userAvatar)

        } catch (e: IndexOutOfBoundsException) {
            showError(true)
        }
    }

    private fun initAdapter() = with(binding) {
        if (isFirstTime) {
            recyclerMain.addItemDecoration(MarginItemDecoration(1, 25, false))
            isFirstTime = false
        }
        try {
            recyclerMain.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            }
        } catch (e: IndexOutOfBoundsException) {
            showError(true)
        }
    }

    private fun loadingBar(isLoading: Boolean) = with(binding) {
        if (isLoading) {
            mainText.visibility = GONE
            loadingBar.visibility = VISIBLE
            mainLayout.visibility = INVISIBLE
            userAvatar.visibility = GONE
            userNickname.visibility = GONE
        } else {
            mainLayout.visibility = VISIBLE
            loadingBar.visibility = GONE
            userAvatar.visibility = VISIBLE
            userNickname.visibility = VISIBLE
        }
    }

    private fun showError(error: Boolean) = with(binding) {
        mainText.visibility = VISIBLE

        if (!error) mainText.text = getString(R.string.nick_error)
        else mainText.text = getString(R.string.error)

        loadingBar.visibility = GONE
        mainLayout.visibility = GONE
        userAvatar.visibility = GONE
        userNickname.visibility = GONE
    }
}