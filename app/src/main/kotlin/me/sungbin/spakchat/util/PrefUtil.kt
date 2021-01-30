/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PrefUtil {
    private const val DEFAULT_KEY = "DataEncryptedSharedPreferences"
    private lateinit var masterKey: MasterKey

    private fun getMasterKey(context: Context): MasterKey {
        if (!::masterKey.isInitialized) {
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .build()

            masterKey = MasterKey.Builder(context).setKeyGenParameterSpec(spec).build()
        }
        return masterKey
    }

    private fun getEncryptedSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            DEFAULT_KEY,
            getMasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun save(context: Context, key: String, value: String) {
        getEncryptedSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun read(context: Context, key: String, _null: String?) =
        getEncryptedSharedPreferences(context).getString(key, _null)
}
