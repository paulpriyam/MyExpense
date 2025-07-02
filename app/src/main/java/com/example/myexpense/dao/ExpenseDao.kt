package com.example.myexpense.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myexpense.entity.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expense WHERE userId = :userId")
    suspend fun getExpensesByUserId(userId: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: String): ExpenseEntity
}