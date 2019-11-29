package com.tigcal.apps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.tigcal.apps.util.ThemeHelper

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_layout, SettingsFragment())
                .commit()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            findPreference<ListPreference>(getString(R.string.settings_theme_preference))
                    ?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener {
                _, newValue ->
                ThemeHelper.applyTheme(newValue as String)
                true
            }
        }
    }
}
