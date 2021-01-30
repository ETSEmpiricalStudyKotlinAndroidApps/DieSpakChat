/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.model.user

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.GlideApp
import me.sungbin.spakchat.R

object ViewBinding {

    private val db = FirebaseStorage.getInstance().reference
    private val storage = FirebaseFirestore.getInstance()

    @JvmStatic
    @BindingAdapter("loadProfile")
    fun loadProfile(imageView: ImageView, user: User) {
        Logger.w(user)
        if (user.isTestMode == true) { // nullable
            GlideApp
                .with(imageView.context)
                .load(user.profileImage ?: ColorDrawable(user.profileImageColor!!))
                .into(imageView)
        } else {
            GlideApp
                .with(imageView.context)
                .load(ColorDrawable(user.profileImageColor!!))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("loadStatus")
    fun loadStatus(imageView: ImageView, isOnline: Boolean) {
        imageView.setImageResource(
            if (isOnline) R.drawable.bg_shape_online
            else R.drawable.bg_shape_offline
        )
    }
}
