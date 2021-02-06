/*
 * Create by Sungbin Ji on 2021. 1. 31.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutUserBinding
import me.sungbin.spakchat.user.model.User

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    private var listener: UserClickListener? = null
    private val differ = AsyncListDiffer(this, FriendsDiffItemCallback())

    inner class ViewHolder(
        private val binding: LayoutUserBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(user: User) {
            with(binding) {
                this.user = user
                root.setOnClickListener {
                    listener?.onUserClick(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_user, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(getUser(position))
    }

    fun setOnUserClickListener(action: User.() -> Unit) {
        listener = object : UserClickListener {
            override fun onUserClick(user: User) {
                action(user)
            }
        }
    }

    private fun getUser(position: Int) = differ.currentList[position]

    fun submit(user: List<User>) {
        differ.submitList(user)
    }

    override fun getItemCount() = differ.currentList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
