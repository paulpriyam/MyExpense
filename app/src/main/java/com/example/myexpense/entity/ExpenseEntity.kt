package com.example.myexpense.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myexpense.entity.ProfileEntity
import java.util.UUID

@Entity(
    tableName = "expense",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Unique identifier for the expense
    val amount: Double, // Amount of the expense
    val description: String, // Description of the expense
    val date: Long, // Date of the expense in milliseconds since epoch
    val category: String, // Category of the expense (e.g., Food, Transport)
    val userId: String // User ID to associate the expense with a specific user
)
