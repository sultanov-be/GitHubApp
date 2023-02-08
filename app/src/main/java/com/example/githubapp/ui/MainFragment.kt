package com.example.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.githubapp.MarginItemDecoration
import com.example.githubapp.RecyclerAdapter
import com.example.githubapp.data.model.User
import com.example.githubapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private val listOfUser: ArrayList<User> = arrayListOf(
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be"),
        User(null, "Sultanov-be")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        viewModel

        recyclerMain.adapter = RecyclerAdapter(listOfUser, requireContext())
        recyclerMain.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        recyclerMain.addItemDecoration(MarginItemDecoration(1, 6, true))
    }
}