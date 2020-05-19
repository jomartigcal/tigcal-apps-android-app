package com.tigcal.apps

import android.app.Application
import androidx.preference.PreferenceManager
import com.tigcal.apps.util.ThemeHelper

class TigcalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = prefs.getString(getString(R.string.settings_theme_preference), null)
        ThemeHelper.applyTheme(themePref)
    }
}
