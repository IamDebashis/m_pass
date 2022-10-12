package com.nide.pocketpass.ui.home

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.databinding.ItemPasswordBinding
import com.nide.pocketpass.util.AESEncryption.decrypt
import com.nide.pocketpass.util.AvaterCreator
import com.nide.pocketpass.util.Const
import com.nide.pocketpass.util.copyToClipBoard
import com.nide.pocketpass.util.toast
import kotlinx.coroutines.*

class PasswordAdapter(
    private val context: Context,
    private val listener: PasswordAdapterListener
) : ListAdapter<PasswordTuple, PasswordAdapter.PasswordViewHolder>(diffCallback) {

    interface PasswordAdapterListener {
        fun onPasswordClick(root: View, password: PasswordTuple)
    }

    private companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PasswordTuple>() {
            override fun areItemsTheSame(oldItem: PasswordTuple, newItem: PasswordTuple): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PasswordTuple,
                newItem: PasswordTuple
            ): Boolean {
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


        fun bind(password: PasswordTuple) {
            binding.password = password


            binding.btnCopy.setOnClickListener {
                if (password.password?.isBlank() == true) {
                    binding.root.context.toast("There are no password !")
                } else {
                    binding.root.context.copyToClipBoard(
                        "password",
                        password.password!!.decrypt() ?: ""
                    )
                    binding.root.context.toast("Password copied")
                }
            }

            binding.passwordContainer.setOnClickListener {
                listener.onPasswordClick(binding.passwordContainer, password)
            }


            Glide.with(binding.ivIcon)
                .load(Const.LOGO_URL+password.name )
                .placeholder(BitmapDrawable(binding.ivIcon.resources, password.icon))
                .error(password.icon)
                .into(binding.ivIcon)
            
        }


    }


}