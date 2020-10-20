package me.sungbin.spakchat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.androidutils.extensions.hide
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.ui.activity.ChatActivity
import org.jetbrains.anko.startActivity


/**
 * Created by SungBin on 2020-07-20.
 */

class FeedChatAdapter(
    private val items: List<Message>
) : RecyclerView.Adapter<FeedChatAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(message: Message, position: Int) {
            with(binding) {
                this.message = message
                ivProfile.setOnClickListener {
                    it.context.startActivity<ChatActivity>("id" to message.owner?.id)
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
        viewholder.bindViewHolder(items[position], position)
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}