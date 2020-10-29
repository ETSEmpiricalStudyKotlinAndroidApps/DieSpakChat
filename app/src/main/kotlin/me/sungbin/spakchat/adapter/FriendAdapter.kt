/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.androidutils.extensions.hide
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFriendBinding
import me.sungbin.spakchat.model.user.User

/**
 * Created by SungBin on 2020-10-29.
 */

class FriendAdapter(
    private val items: List<User>
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutFriendBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(user: User, position: Int) {
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
        viewholder.bindViewHolder(items[position], position)
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
