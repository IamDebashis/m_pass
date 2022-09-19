package com.nide.mpass.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nide.mpass.R
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.ItemPasswordBinding
import com.nide.mpass.util.AvaterCreator
import com.nide.mpass.util.RandomColors

class PasswordAdapter(private val onItemClick:(Password)->Unit) : ListAdapter<Password, PasswordAdapter.PasswordViewHolder>(diffCallback) {

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
                binding.apply {
                    tvTitle.text = password.name
                   tvUserId.text = password.userId
                }
                binding.tvTitle.transitionName = binding.tvTitle.context.getString(R.string.password_to_password_details_transaction, password.id.toString())
                binding.ivIcon.transitionName = binding.tvTitle.context.getString(R.string.password_to_password_details_image_transaction, password.id.toString())

                binding.root.setOnClickListener {
                    onItemClick.invoke(password)
                }

                val image = AvaterCreator.AvatarBuilder(binding.root.context)
                    .setLabel(password.name)
                    .setAvatarSize(200)
                    .setTextSize(50)
                    .toCircle()
                    .setBackgroundColor(RandomColors().getColor())
                    .build()

                binding.ivIcon.setImageDrawable(image)

            }


    }


}