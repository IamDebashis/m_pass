package com.nide.mpass.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.ItemSecPasswordBinding
import com.nide.mpass.util.AvaterCreator
import com.nide.mpass.password_util.PasswordStrength
import com.nide.mpass.util.AESEncryption.decrypt
import com.nide.mpass.util.setProgressWithColor
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class SecPasswordAdapter(private val onItemClick: (Password) -> Unit) :
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

        fun bind(password: Password) = CoroutineScope(Dispatchers.Main).launch {

            binding.root.setOnClickListener {
                onItemClick.invoke(password)
            }
            val score = async(Dispatchers.Default){ getSequreValue(password.password ?: "") }
            async {
                Glide.with(binding.root.context)
                    .load(
                        AvaterCreator.AvatarBuilder(binding.root.context).setAvatarSize(200)
                            .setTextSize(50).setLabel(password.name).toCircle().build()
                    )
                    .into(binding.ivIcon)
            }
            binding.progressBar.setProgressWithColor(score.await())
        }

        private suspend fun getSequreValue(password: String): Int = coroutineScope {
            val decryptPassword = password.decrypt()
            return@coroutineScope PasswordStrength(decryptPassword ?: "").check()
        }


    }


}