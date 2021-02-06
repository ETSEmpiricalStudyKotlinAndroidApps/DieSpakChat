/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.room

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import me.sungbin.spakchat.GlideApp

object RoomBinding {

    @JvmStatic
    @BindingAdapter("spak_loadCover")
    fun loadCover(imageView: ImageView, image: String) {
        GlideApp
            .with(imageView.context)
            .load(image)
            .into(imageView)
    }
}
