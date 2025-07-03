package com.example.myexpense.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "investment",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class InvestmentEntity(
    @PrimaryKey
    var investmentId: String = UUID.randomUUID().toString(), // Unique identifier for the investment
    var investmentName: String,
    var investmentAmount: Double,
    var investmentDate: String,
    var investmentType: String,
    var investmentDescription: String,
    val userId: String,
    val sipType: String?="",
    val sipAmount: Double?= 0.0,
    val fdType: String?="",
    val fdAmount: Double?= 0.0,
    val fdDuration: String?="",
    val fdInterest: Double?= 0.0,
    val fdMaturityDate: String?="",
    val fdMaturityAmount: Double?= 0.0,
    val sharePrice: Double?= 0.0,
    val shareQuantity: Double?= 0.0,
)
