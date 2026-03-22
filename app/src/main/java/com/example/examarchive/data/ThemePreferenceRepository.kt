package com.example.examarchive.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.examarchive.ui.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val THEME_DATA_STORE = "theme_preferences"

val Context.themeDataStore by preferencesDataStore(name = THEME_DATA_STORE)

class ThemePreferenceRepository(private val dataStore: androidx.datastore.core.DataStore<Preferences>) {
    private object Keys {
        val theme = stringPreferencesKey("theme_option")
    }

    val themeOption: Flow<ThemeOption> = dataStore.data.map { prefs ->
        val saved = prefs[Keys.theme]
        saved?.let { runCatching { ThemeOption.valueOf(it) }.getOrNull() } ?: ThemeOption.System
    }

    suspend fun setTheme(option: ThemeOption) {
        dataStore.edit { prefs ->
            prefs[Keys.theme] = option.name
        }
    }
}
