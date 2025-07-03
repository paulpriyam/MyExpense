package com.example.myexpense.ui.screens.investment.repository

import com.example.myexpense.dao.InvestmentDao
import com.example.myexpense.entity.InvestmentEntity
import javax.inject.Inject

class InvestmentRepositoryImpl @Inject constructor(
    private val investmentDao: InvestmentDao
) : InvestmentRepository {

    override suspend fun getInvestmentByUserId(userId: String): List<InvestmentEntity> {
        return investmentDao.getInvestmentByUserId(userId)
    }

    override suspend fun insertInvestment(investment: InvestmentEntity) {
        investmentDao.insertInvestment(investment)
    }

    override suspend fun getInvestmentByTypeAndUserId(
        type: String,
        userId: String
    ): List<InvestmentEntity> {
        return investmentDao.getInvestmentByTypeAndUserId(type, userId)
    }
}