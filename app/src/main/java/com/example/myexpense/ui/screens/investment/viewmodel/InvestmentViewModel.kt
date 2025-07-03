package com.example.myexpense.ui.screens.investment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.ui.screens.investment.usecase.InvestmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentViewModel @Inject constructor(private val investmentUseCase: InvestmentUseCase): ViewModel() {
    // Placeholder for Investment ViewModel
    // This can be expanded later with actual logic for managing investments
    // For now, we can just have a simple structure or methods that will be implemented later
    // Example:
    // fun addInvestment(investment: Investment) {
    //     // Logic to add an investment
    // }

    fun insertInvestment(investment: InvestmentEntity) {
        // Logic to insert an investment
        // This will call the use case to handle the insertion
        viewModelScope.launch(Dispatchers.IO) {
            investmentUseCase.insertInvestment(investment)
        }
    }
}