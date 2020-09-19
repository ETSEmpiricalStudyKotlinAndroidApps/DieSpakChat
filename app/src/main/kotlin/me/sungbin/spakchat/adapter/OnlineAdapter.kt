package me.sungbin.spakchat.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedOnlineBinding
import me.sungbin.spakchat.model.User


/**
 * Created by SungBin on 2020-07-20.
 */

class OnlineAdapter(
    private val items: List<User>,
    private val activity: Activity
) : RecyclerView.Adapter<OnlineAdapter.ViewHolder>() {

    class ViewHolder(private val onlineBinding: LayoutFeedOnlineBinding) :
        RecyclerView.ViewHolder(onlineBinding.root) {

        fun bindViewHolder(user: User) {
            with(onlineBinding) {
                this.user = user
                invalidateAll()
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
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