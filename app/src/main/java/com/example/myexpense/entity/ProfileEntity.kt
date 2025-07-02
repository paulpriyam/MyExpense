package com.example.myexpense.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val userId: String = UUID.randomUUID().toString(), // Added userId for profile identification
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val dob: Long = 0L,
    val profilePictureUrl: String? = null
)
