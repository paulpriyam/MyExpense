package com.example.myexpense.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getUserId(): String? {
        return preferences.getString(KEY_USER_ID, null)
    }

    fun saveUserId(userId: String) {
        preferences.edit { putString(KEY_USER_ID, userId) }
    }

    companion object {
        private const val PREF_NAME = "my_expense_prefs"
        private const val KEY_USER_ID = "user_id"
    }
}
