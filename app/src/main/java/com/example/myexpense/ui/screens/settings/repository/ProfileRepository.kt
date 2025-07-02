package com.example.myexpense.ui.screens.settings.repository

import com.example.myexpense.entity.ProfileEntity

interface ProfileRepository {

    suspend fun getProfileById(id: String): ProfileEntity?

    fun insertProfile(profile: ProfileEntity)

    suspend fun updateProfile(profile: ProfileEntity)

    suspend fun deleteProfile(profile: ProfileEntity)


}