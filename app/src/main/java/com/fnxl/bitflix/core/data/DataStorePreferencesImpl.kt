package com.fnxl.bitflix.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.util.Constants.PREFERENCES_KEY_ACCESS_TOKEN
import com.fnxl.bitflix.core.util.Constants.PREFERENCES_KEY_REFRESH_TOKEN
import com.fnxl.bitflix.core.util.Constants.PREFERENCES_KEY_SPLASH_SCREEN
import com.fnxl.bitflix.core.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStorePreferencesImpl(context: Context) : DataStorePreferences {

    private object AccessToken {
        val key = stringPreferencesKey(name = PREFERENCES_KEY_ACCESS_TOKEN)
    }

    private object RefreshToken {
        val key = stringPreferencesKey(name = PREFERENCES_KEY_REFRESH_TOKEN)
    }

    private object SplashCompleted {
        val key = booleanPreferencesKey(name = PREFERENCES_KEY_SPLASH_SCREEN)
    }

    override val dataStore = context.dataStore

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AccessToken.key] = token
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[RefreshToken.key] = token
        }
    }

    override suspend fun readAccessToken(): Flow<String> {
        return dataStore.data.map {
            it[AccessToken.key] ?: ""
        }
    }

    override suspend fun readRefreshToken(): Flow<String> {
        return dataStore.data.map {
            it[RefreshToken.key] ?: ""
        }
    }

    override suspend fun readSplashCompleted(): Flow<Boolean> {
        return dataStore.data.map {
            it[SplashCompleted.key] ?: false
        }
    }

    override suspend fun saveSplashCompleted(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[SplashCompleted.key] = state
        }
    }

}