package com.example.myexpense.ui.screens.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpense.entity.CategoryEntity
import com.example.myexpense.entity.ExpenseEntity
import com.example.myexpense.ui.screens.expense.repository.ExpenseRepository
import com.example.myexpense.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _expense = MutableStateFlow<ExpenseEntity?>(null)
    val expense: StateFlow<ExpenseEntity?> = _expense.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    // Default categories
    private val defaultCategories = listOf("All", "Food", "Transport", "Utility")

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            val userId = preferenceManager.getUserId()
            if (userId != null) {
                val userCategories = withContext(Dispatchers.IO) {
                    expenseRepository.getCategoriesByUserId(userId).map { it.name }
                }

                // Combine default categories with user-created ones, removing duplicates
                val allCategories = (defaultCategories + userCategories).distinct()
                _categories.value = allCategories
            } else {
                _categories.value = defaultCategories
            }
        }
    }

    fun addCategory(categoryName: String) {
        viewModelScope.launch {
            val userId = preferenceManager.getUserId() ?: return@launch
            val newCategory = CategoryEntity(
                name = categoryName,
                userId = userId
            )

            withContext(Dispatchers.IO) {
                expenseRepository.insertCategory(newCategory)
            }

            // Update categories list
            val updatedCategories = (_categories.value + categoryName).distinct()
            _categories.value = updatedCategories
        }
    }

    fun insertExpense(expense: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.insertExpense(expense)

            // If the category is not in our default list, add it as a user category
            if (!defaultCategories.contains(expense.category) &&
                !_categories.value.contains(expense.category)) {
                addCategory(expense.category)
            }
        }
    }

    fun getExpenseByUserId(userId: String) {
        viewModelScope.launch {
            _expenses.value = expenseRepository.getExpenseByUserId(userId)
        }
    }

    fun getExpenseById(expenseId: String) {
        viewModelScope.launch {
            _expense.value = expenseRepository.getExpenseById(expenseId)
        }
    }
}

