package com.example.myexpense.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myexpense.entity.ProfileEntity

@Dao
interface ProfileDao {
//     Define methods for accessing the ProfileEntity data
//     For example:
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertProfile(profile: ProfileEntity)

     @Query("SELECT * FROM profile WHERE id = :id")
     suspend fun getProfileById(id: String): ProfileEntity?

     @Update
     suspend fun updateProfile(profile: ProfileEntity)

     @Delete
     suspend fun deleteProfile(profile: ProfileEntity)
}