package com.example.myexpense.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "profile")

data class ProfileEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val dob: Long? = null,
    val profilePictureUrl: String? = null
)
