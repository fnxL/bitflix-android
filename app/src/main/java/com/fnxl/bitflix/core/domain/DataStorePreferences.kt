package com.fnxl.bitflix.core.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    val dataStore: DataStore<Preferences>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun readAccessToken(): Flow<String>
    suspend fun readRefreshToken(): Flow<String>
    suspend fun readSplashCompleted(): Flow<Boolean>
    suspend fun saveSplashCompleted(state: Boolean)
}