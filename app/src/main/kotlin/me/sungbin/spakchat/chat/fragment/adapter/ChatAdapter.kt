/*
 * Create by Sungbin Ji on 2021. 2. 7.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.chat.model.Chat
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ChatDiffItemCallback())
    private var listener: ChatClickInterface? = null

    fun setOnChatClickListener(action: Chat.() -> Unit) {
        listener = object : ChatClickInterface {
            override fun onChatClicked(chat: Chat) {
                action(chat)
            }
        }
    }

    inner class ViewHolder(
        private val binding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(chat: Chat) {
            with(binding) {
                this.chat = chat
                binding.root.setOnClickListener {
                    listener?.onChatClicked(chat)
                }
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
        viewholder.bindViewHolder(getItem(position))
    }

    fun submit(items: MutableList<Chat>) {
        differ.submitList(items)
    }

    private fun getItem(position: Int) = differ.currentList[position]

    override fun getItemCount() = differ.currentList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
