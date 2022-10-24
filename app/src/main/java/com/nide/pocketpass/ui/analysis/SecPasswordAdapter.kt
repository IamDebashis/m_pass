package com.nide.pocketpass.ui.analysis

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.databinding.ItemSecPasswordBinding
import com.nide.pocketpass.util.AESEncryption.decrypt
import com.nide.pocketpass.util.AvaterCreator
import com.nide.pocketpass.util.Const
import com.nide.pocketpass.util.password_util.PasswordStrength
import com.nide.pocketpass.util.setProgressWithColor
import kotlinx.coroutines.*

class SecPasswordAdapter(val context: Context, private val onItemClick: (Password) -> Unit) :
    ListAdapter<Password, SecPasswordAdapter.SecPasswordViewHolder>(secPasswordCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecPasswordViewHolder {
        return SecPasswordViewHolder(
            ItemSecPasswordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SecPasswordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private companion object {
        val secPasswordCallback = object : DiffUtil.ItemCallback<Password>() {
            override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
                return oldItem == newItem
            }
        }

    }


    inner class SecPasswordViewHolder(private val binding: ItemSecPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(password: Password) {
            binding.root.setOnClickListener {
                onItemClick(password)
            }
            binding.apply {
                tvTitle.text = password.name
                tvUserId.text = password.userId
            }
            Glide.with(binding.ivIcon)
                .load(Const.LOGO_URL+password.name )
                .placeholder(BitmapDrawable(binding.ivIcon.resources, password.icon))
                .error(password.icon)
                .into(binding.ivIcon)

                binding.progressBar.setProgressWithColor(password.strength)
                binding.tvStrength.text = PasswordStrength.mapStrength(password.strength)


        }

    /*    private suspend fun loadPasswordStrength(password: Password) = withContext(Dispatchers.IO) {
            val decryptPassword = password.password?.decrypt()
            val strength = PasswordStrength(decryptPassword ?: "")
            strength.check()
            strength
        }*/
    }


}