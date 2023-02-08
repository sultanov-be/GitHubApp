package com.example.githubapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.data.model.ReposListItem
import com.example.githubapp.databinding.ItemUsersBinding

class RecyclerAdapter internal constructor(
    private val listOfUsers: ArrayList<ReposListItem>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ReposListItem>() {
        override fun areItemsTheSame(oldItem: ReposListItem, newItem: ReposListItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ReposListItem, newItem: ReposListItem): Boolean {
            return oldItem == newItem
        }
    }
    class ViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val user = listOfUsers[position]

        textView.text = user.name
        linkButton.setOnClickListener {
            //TODO go to link
        }
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }
}