package com.example.myexpense.ui.screens.settings.repository

import com.example.myexpense.dao.ProfileDao
import com.example.myexpense.entity.ProfileEntity
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDao: ProfileDao) :
    ProfileRepository {

    // Placeholder for ProfileRepository implementation
    // This can be expanded later with actual logic for managing profiles
    // For now, we can just have a simple structure or methods that will be implemented later
    // Example:
    // override suspend fun getProfileById(id: String): ProfileEntity? {
    //     // Logic to get profile by ID
    //     return null
    // }
    override suspend fun getProfileById(id: String): ProfileEntity? {
        return profileDao.getProfileById(id)
            ?: throw IllegalArgumentException("Profile with id $id not found")
    }

    override fun insertProfile(profile: ProfileEntity) {
        profileDao.insertProfile(profile)

    }

    override suspend fun updateProfile(profile: ProfileEntity) {
        profileDao.updateProfile(profile)
    }

    override suspend fun deleteProfile(profile: ProfileEntity) {
        profileDao.deleteProfile(profile)
    }
}