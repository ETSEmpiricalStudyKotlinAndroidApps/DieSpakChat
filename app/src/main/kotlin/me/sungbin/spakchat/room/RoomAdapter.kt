/*
 * Create by Sungbin Ji on 2021. 2. 9.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutFeedChatBinding

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, RoomDiffItemCallback())
    private var listener: RoomClickInterface? = null

    fun setOnChatClickListener(action: Room.() -> Unit) {
        listener = object : RoomClickInterface {
            override fun onRoomClicked(room: Room) {
                action(room)
            }
        }
    }

    inner class ViewHolder(
        private val binding: LayoutFeedChatBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewHolder(room: Room) {
            with(binding) {
                this.room = room
                binding.root.setOnClickListener {
                    listener?.onRoomClicked(room)
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

    fun submit(items: MutableList<Room>) {
        differ.submitList(items)
    }

    private fun getItem(position: Int) = differ.currentList[position]

    override fun getItemCount() = differ.currentList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
