package com.huarrrr.gankio_compose.storage

import androidx.datastore.preferences.core.stringPreferencesKey
import com.huarrrr.gankio_compose.App

object AppPreferences {
    private val KEY_USER = stringPreferencesKey("user")

    private val dataStore by lazy {
        PreferencesDataStore(App.context, App.context.packageName + ".app")
    }
}