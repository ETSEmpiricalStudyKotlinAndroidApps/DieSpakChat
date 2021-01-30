/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.chat

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutOtherChatBinding
import me.sungbin.spakchat.databinding.LayoutOwnChatBinding
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageViewType
import me.sungbin.spakchat.ui.activity.DetailImageActivity

class ChatAdapter(
    private val items: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class OtherChatViewHolder(
        private val binding: LayoutOtherChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(message: Message) {
            with(binding) {
                this.message = message
                ivProfile.setOnClickListener {
                    val context = it.context
                    val transitionName = context.getString(R.string.image_transition)
                    val intent = Intent(context, DetailImageActivity::class.java)
                        .putExtra("image", message.owner?.profileImageColor)
                        .putExtra("name", message.owner?.name)
                        .putExtra("avatar", message.owner?.profileImageColor)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        ivProfile, transitionName
                    )
                    context.startActivity(intent, options.toBundle())
                }
            }
        }
    }

    inner class OwnChatViewHolder(
        private val binding: LayoutOwnChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(message: Message) {
            with(binding) {
                this.message = message
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = when (viewType) {
        MessageViewType.OTHER -> OtherChatViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_other_chat, viewGroup, false
            )
        )
        MessageViewType.OWN -> OwnChatViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_own_chat, viewGroup, false
            )
        )
        else -> throw Exception("없는 뷰 홀더")
    }

    override fun onBindViewHolder(@NonNull viewholder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].messageViewType!!) {
            MessageViewType.OWN -> (viewholder as OwnChatViewHolder).bindViewHolder(items[position])
            MessageViewType.OTHER -> (viewholder as OtherChatViewHolder).bindViewHolder(items[position])
            else -> throw Exception("없는 뷰 홀더")
        }
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = items[position].messageViewType!!
}
