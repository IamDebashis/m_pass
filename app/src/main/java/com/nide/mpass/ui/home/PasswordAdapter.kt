package com.nide.mpass.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.ItemPasswordBinding

class PasswordAdapter : ListAdapter<Password, PasswordAdapter.PasswordViewHolder>(diffCallback) {

    private companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Password>() {
            override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        return PasswordViewHolder(
            ItemPasswordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
       val password = getItem(position)
        holder.bind(password)

    }

    inner class PasswordViewHolder(private val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(password: Password){

            }

    }


}