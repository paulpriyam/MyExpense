package com.example.myexpense.ui.screens.expense.repository

import com.example.myexpense.entity.CategoryEntity
import com.example.myexpense.entity.ExpenseEntity
import javax.inject.Inject

interface ExpenseRepository {
     suspend fun getExpenseByUserId(userId: String): List<ExpenseEntity>
     fun insertExpense(expense: ExpenseEntity)
     suspend fun getExpenseById(expenseId: String): ExpenseEntity?

     // Category operations
     suspend fun getCategoriesByUserId(userId: String): List<CategoryEntity>
     suspend fun insertCategory(category: CategoryEntity)
     suspend fun getCategoryByName(name: String, userId: String): CategoryEntity?
}

