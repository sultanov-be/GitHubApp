package com.example.githubapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.model.User
import com.example.githubapp.databinding.ItemUsersBinding

class RecyclerAdapter internal constructor(
    private val listOfUsers: ArrayList<User>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val user = listOfUsers[position]

        if (user.avatar_url == null || user.avatar_url.isEmpty()) {
            Glide.with(context).load(R.drawable.loading_img)
                .into(imageView)
        } else {
            Glide.with(context).load(user.avatar_url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.loading_img)
                .into(imageView)
        }

        textView.text = user.login
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }
}