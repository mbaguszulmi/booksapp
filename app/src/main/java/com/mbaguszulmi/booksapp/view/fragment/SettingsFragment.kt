package com.mbaguszulmi.booksapp.view.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mbaguszulmi.booksapp.R
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val languagePreference = findPreference<ListPreference>(getString(R.string.pref_key_language))
        languagePreference?.setOnPreferenceChangeListener { _, newValue ->
            Log.d("SettingsFragment", "Language changed to $newValue")
            setLocale(newValue as String)
            true
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = requireActivity().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requireActivity().createConfigurationContext(config)
        }
        resources.updateConfiguration(config, resources.displayMetrics)

        // relaunch app
        val intent = requireContext().packageManager.getLaunchIntentForPackage(
            requireContext().packageName
        )
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}