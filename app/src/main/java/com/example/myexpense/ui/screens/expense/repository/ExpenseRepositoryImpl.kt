package com.example.myexpense.ui.screens.expense.repository

import com.example.myexpense.dao.CategoryDao
import com.example.myexpense.dao.ExpenseDao
import com.example.myexpense.entity.CategoryEntity
import com.example.myexpense.entity.ExpenseEntity
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao
) : ExpenseRepository {

    // Placeholder for Expense Repository implementation
    // This can be expanded later with actual logic for managing expenses
    // For now, we can just have a simple structure or methods that will be implemented later

    override suspend fun getExpenseByUserId(userId: String): List<ExpenseEntity> {
        // Logic to get expense by user ID
        return expenseDao.getExpensesByUserId(userId)
    }

    override fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    override suspend fun getExpenseById(expenseId: String): ExpenseEntity? {
        // Logic to get expense by ID
        return expenseDao.getExpenseById(expenseId)
    }

    override suspend fun getCategoriesByUserId(userId: String): List<CategoryEntity> {
        return categoryDao.getCategoriesByUserId(userId)
    }

    override suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    override suspend fun getCategoryByName(name: String, userId: String): CategoryEntity? {
        return categoryDao.getCategoryByNameAndUserId(name, userId)
    }
}

