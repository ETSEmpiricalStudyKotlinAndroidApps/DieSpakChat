package me.sungbin.spakchat.util

import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map
import me.sungbin.spakchat.SpakChat.Companion.context

object SettingUtil {
    private val dataStore = context.createDataStore(
        name = "setting",
    )

    private fun getKey(key: String) = preferencesKey<Any>(key)

    suspend fun save(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[getKey(key)] = value
        }
    }

    // todo: 무조건 LiveData를 써야 할 까?
    fun read(key: String): Any {
        return dataStore.data.map {
            return@map it[getKey(key)]
        }
    }
}