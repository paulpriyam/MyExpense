package com.example.myexpense.ui.screens.expense.usecase

import com.example.myexpense.ui.screens.expense.repository.ExpenseRepository
import javax.inject.Inject

class ExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend fun getExpenseByUserId(userId: String) =
        expenseRepository.getExpenseByUserId(userId)

    fun insertExpense(expense: com.example.myexpense.entity.ExpenseEntity) =
        expenseRepository.insertExpense(expense)

    suspend fun getExpenseById(expenseId: String) =
        expenseRepository.getExpenseById(expenseId)
}