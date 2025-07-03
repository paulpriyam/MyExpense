package com.example.myexpense.ui.screens.investment.usecase

import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.ui.screens.investment.repository.InvestmentRepository
import javax.inject.Inject

class InvestmentUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {

    suspend fun getInvestmentByUserId(userId: String) =
        investmentRepository.getInvestmentByUserId(userId)

    suspend fun insertInvestment(investment: InvestmentEntity) =
        investmentRepository.insertInvestment(investment)

    suspend fun getInvestmentByTypeAndUserId(type: String, userId: String) =
        investmentRepository.getInvestmentByTypeAndUserId(type, userId)
}