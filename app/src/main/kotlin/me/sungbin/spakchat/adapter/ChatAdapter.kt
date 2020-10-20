package me.sungbin.spakchat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutOtherChatBinding
import me.sungbin.spakchat.databinding.LayoutOwnChatBinding
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageViewType


/**
 * Created by SungBin on 2020-07-20.
 */

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
                invalidateAll()
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
                invalidateAll()
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