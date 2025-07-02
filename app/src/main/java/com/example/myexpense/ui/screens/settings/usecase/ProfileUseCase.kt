package com.example.myexpense.ui.screens.settings.usecase

import com.example.myexpense.entity.ProfileEntity
import com.example.myexpense.ui.screens.settings.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun getProfile(id: String) = profileRepository.getProfileById(id)

    suspend fun insertProfile(profile: ProfileEntity) = profileRepository.insertProfile(profile)

    suspend fun updateProfile(profile: ProfileEntity) = profileRepository.updateProfile(profile)

    suspend fun deleteProfile(profile: ProfileEntity) = profileRepository.deleteProfile(profile)
}

