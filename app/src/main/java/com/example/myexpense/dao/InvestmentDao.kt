package com.example.myexpense.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.ui.screens.Screens

@Dao
interface InvestmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvestment(investment: InvestmentEntity)

    @Query("SELECT * FROM investment WHERE userId = :userId")
    suspend fun getInvestmentByUserId(userId: String): List<InvestmentEntity>

    @Query("DELETE FROM investment WHERE investmentId = :id")
    suspend fun deleteInvestmentById(id: Int)

    @Query("SELECT * FROM investment where investmentType = :type AND userId = :userId")
    suspend fun getInvestmentByTypeAndUserId(type: String, userId: String): List<InvestmentEntity>
}