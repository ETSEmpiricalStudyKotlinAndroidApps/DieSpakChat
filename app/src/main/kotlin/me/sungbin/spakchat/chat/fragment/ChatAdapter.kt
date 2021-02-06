/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.fragment

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.spakchat.R
import me.sungbin.spakchat.chat.activity.ChatActivity
import me.sungbin.spakchat.chat.model.Chat
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding

class ChatAdapter(
    private val items: List<Chat>,
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(chat: Chat) {
            with(binding) {
                this.chat = chat
                ivProfile.setOnClickListener {
                    (it.context as Activity).startActivity<ChatActivity>(
                        false,
                        "id" to chat.owner?.userId,

                    )
                }
                invalidateAll()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_feed_chat, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
