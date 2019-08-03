package me.loidsemus.debtomania.screens.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import me.loidsemus.debtomania.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs, rootKey)
    }

}