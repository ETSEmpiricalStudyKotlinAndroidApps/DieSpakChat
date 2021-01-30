/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.androidutils.extensions.hide
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFriendBinding
import me.sungbin.spakchat.model.user.User

class FriendAdapter(
    private val items: List<User>
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutFriendBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(user: User) {
            with(binding) {
                this.user = user
                if (adapterPosition == 0) viewTop.hide(true)
                invalidateAll()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_friend, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
