/*
 * Create by Sungbin Ji on 2021. 1. 29.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 */

package me.sungbin.spakchat.ui.fragment.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedOnlineBinding
import me.sungbin.spakchat.model.user.User

/**
 * Created by SungBin on 2020-07-20.
 */

class OnlineAdapter(
    private val items: List<User>
) : RecyclerView.Adapter<OnlineAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LayoutFeedOnlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(user: User) {
            with(binding) {
                this.user = user
                invalidateAll()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_feed_online, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
