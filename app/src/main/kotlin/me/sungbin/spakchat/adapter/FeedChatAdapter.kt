package me.sungbin.spakchat.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.sungbintool.extensions.hide
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding
import me.sungbin.spakchat.model.message.Message


/**
 * Created by SungBin on 2020-07-20.
 */

class FeedChatAdapter(
    private val items: List<Message>,
    private val activity: Activity
) : RecyclerView.Adapter<FeedChatAdapter.ViewHolder>() {

    class ViewHolder(
        private val feedChatBinding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(feedChatBinding.root) {

        fun bindViewHolder(message: Message, position: Int) {
            with(feedChatBinding) {
                this.message = message
                if (adapterPosition == 0) viewTop.hide(true)
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
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