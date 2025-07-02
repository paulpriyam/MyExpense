package com.example.myexpense.ui.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpense.entity.ProfileEntity
import com.example.myexpense.ui.screens.settings.usecase.ProfileUseCase
import com.example.myexpense.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    val profileUseCase: ProfileUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileEntity?>(null)
    val profileState: StateFlow<ProfileEntity?> = _profileState.asStateFlow()

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                // Get userId from SharedPreferences
                val userId = preferenceManager.getUserId()

                // If userId exists, try to get profile by userId
                if (userId != null) {
                    val profile = profileUseCase.getProfile(userId)
                    if (profile != null) {
                        _profileState.value = profile
                        return@launch
                    }
                }
            } catch (e: Exception) {
                // Handle error case
            }
        }
    }

    // Save or update profile
    suspend fun saveOrUpdateProfile(profile: ProfileEntity) {
        viewModelScope.launch(Dispatchers.IO){
            val userId = preferenceManager.getUserId() ?: UUID.randomUUID().toString().also {
                preferenceManager.saveUserId(it)
            }

            // Create updated profile with the saved/new userId
            val updatedProfile = profile.copy(userId = userId)

            profileUseCase.insertProfile(updatedProfile)
            _profileState.value = updatedProfile
        }
    }
}
