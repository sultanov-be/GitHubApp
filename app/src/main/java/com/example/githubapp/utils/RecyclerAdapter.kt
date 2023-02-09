package com.example.githubapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.data.model.ReposListItem
import com.example.githubapp.databinding.ItemUsersBinding

class RecyclerAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ReposListItem>() {
        override fun areItemsTheSame(oldItem: ReposListItem, newItem: ReposListItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ReposListItem, newItem: ReposListItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val user = differ.currentList[position]

        textView.text = user.name

        linkButton.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user.html_url)))
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}