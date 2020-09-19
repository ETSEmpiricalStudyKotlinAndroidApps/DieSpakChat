package me.sungbin.spakchat.util

import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import me.sungbin.spakchat.SpakChat.Companion.context

object DataUtil {

    val dataStore = context.createDataStore(
        name = "account",
    )

    fun getKey(key: String) = preferencesKey<Any>(key)

    suspend fun save(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[getKey(key)] = value
        }
    }
}