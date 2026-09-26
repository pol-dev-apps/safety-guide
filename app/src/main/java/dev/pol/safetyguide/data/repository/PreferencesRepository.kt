package dev.pol.safetyguide.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesRepository(
    private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "settings"

        val HOUSEHOLD_SIZE_KEY = intPreferencesKey("household_size")
        val DISCLAIMER_ACCEPTED_KEY = booleanPreferencesKey("disclaimer_accepted")
        val HOME_DAYS_KEY = intPreferencesKey("home_days")
        val EVAC_DAYS_KEY = intPreferencesKey("evac_days")

        const val DEFAULT_HOUSEHOLD_SIZE = 4
        const val DEFAULT_HOME_DAYS = 3
        const val DEFAULT_EVAC_DAYS = 1

        fun isDisclaimerAccepted(context: Context): Boolean {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean("disclaimer_accepted", false)
        }
    }

    val householdSize: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[HOUSEHOLD_SIZE_KEY] ?: DEFAULT_HOUSEHOLD_SIZE
        }

    val homeDays: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[HOME_DAYS_KEY] ?: DEFAULT_HOME_DAYS
        }

    val evacDays: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[EVAC_DAYS_KEY] ?: DEFAULT_EVAC_DAYS
        }

    val isDisclaimerAccepted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DISCLAIMER_ACCEPTED_KEY] ?: false
        }

    suspend fun acceptDisclaimer() {
        context.dataStore.edit { preferences ->
            preferences[DISCLAIMER_ACCEPTED_KEY] = true
        }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean("disclaimer_accepted", true).apply()
    }

    suspend fun updateHouseholdSize(newSize: Int) {
        context.dataStore.edit { preferences ->
            preferences[HOUSEHOLD_SIZE_KEY] = newSize
        }
    }

    suspend fun updateHomeDays(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[HOME_DAYS_KEY] = days
        }
    }

    suspend fun updateEvacDays(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[EVAC_DAYS_KEY] = days
        }
    }
}
