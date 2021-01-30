/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment.chat

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.androidutils.extensions.hide
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.ui.activity.chat.ChatActivity

class FeedChatAdapter(
    private val items: List<Message>,
) : RecyclerView.Adapter<FeedChatAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(message: Message) {
            with(binding) {
                this.message = message
                ivProfile.setOnClickListener {
                    (it.context as Activity).startActivity<ChatActivity>(
                        false,
                        "id" to message.owner?.id
                    )
                }
                if (adapterPosition == 0) viewTop.hide(true)
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
