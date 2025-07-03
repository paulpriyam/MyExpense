package com.example.myexpense.ui.screens.investment.repository

import com.example.myexpense.entity.InvestmentEntity

interface InvestmentRepository {

    suspend fun getInvestmentByUserId(userId: String): List<InvestmentEntity>

    suspend fun insertInvestment(investment: InvestmentEntity)

    suspend fun getInvestmentByTypeAndUserId(type: String, userId: String): List<InvestmentEntity>
}