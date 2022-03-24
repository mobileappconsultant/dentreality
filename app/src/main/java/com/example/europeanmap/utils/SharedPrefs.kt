package com.example.europeanmap.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import androidx.security.crypto.MasterKeys.getOrCreate
import com.example.europeanmap.ui.model.Country
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(@ApplicationContext context: Context, val gson: Gson) {

    companion object {
        const val PREFS_NAME = "CountryHome_APP"
        const val SAVED_HOME = "SaveHomeLocation"
    }

    private var masterKeyAlias = getOrCreate(AES256_GCM_SPEC)

    private var prefs = EncryptedSharedPreferences.create(
        PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @SuppressLint("CommitPrefEdits")
    fun saveHome(country: Country) {
        val editor = prefs.edit()
        val cardToJson: String = gson.toJson(country)
        editor.putString(SAVED_HOME, cardToJson)
        editor.apply()
    }

    fun getHomeCountry(): Country? {
        return if (prefs.contains(SAVED_HOME)) {
            val jsonCountry = prefs.getString(SAVED_HOME, null)
            gson.fromJson(jsonCountry, Country::class.java)
        } else return null
    }
}
