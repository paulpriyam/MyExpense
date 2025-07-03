package com.example.myexpense.di

import android.content.Context
import androidx.room.Room
import com.example.myexpense.dao.CategoryDao
import com.example.myexpense.dao.ExpenseDao
import com.example.myexpense.dao.InvestmentDao
import com.example.myexpense.dao.ProfileDao
import com.example.myexpense.db.ExpenseDatabase
import com.example.myexpense.ui.screens.expense.repository.ExpenseRepository
import com.example.myexpense.ui.screens.expense.repository.ExpenseRepositoryImpl
import com.example.myexpense.ui.screens.expense.usecase.ExpenseUseCase
import com.example.myexpense.ui.screens.investment.repository.InvestmentRepository
import com.example.myexpense.ui.screens.investment.repository.InvestmentRepositoryImpl
import com.example.myexpense.ui.screens.investment.usecase.InvestmentUseCase
import com.example.myexpense.ui.screens.settings.repository.ProfileRepository
import com.example.myexpense.ui.screens.settings.repository.ProfileRepositoryImpl
import com.example.myexpense.ui.screens.settings.usecase.ProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(context: Context): ExpenseDatabase {
        // Implementation to provide an instance of ExpenseDatabase
        // This could involve creating a Room database instance or any other database setup
        return Room.databaseBuilder(
            context = context,
            ExpenseDatabase::class.java,
            "expense_database"
        )
            .addMigrations(ExpenseDatabase.MIGRATION_1_2)
            .addMigrations(ExpenseDatabase.MIGRATION_2_3) // Add migrations as needed
            // Alternatively, you can use fallbackToDestructiveMigration() instead of addMigrations()
            // .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        // Provides the application context
        return context
    }

    @Provides
    fun provideProfileDao(database: ExpenseDatabase) = database.profileDao()


    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao {
        return database.expenseDao()
    }

    @Provides
    fun provideCategoryDao(database: ExpenseDatabase) = database.categoryDao()


    @Provides
    fun provideProfileRepository(profileDao: ProfileDao): ProfileRepository {
        return ProfileRepositoryImpl(profileDao)
    }

    @Provides
    fun provideExpenseRepository(
        expenseDao: ExpenseDao,
        categoryDao: CategoryDao
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(
            expenseDao,
            categoryDao
        ) // Assuming ExpenseRepositoryImpl is similar to ProfileRepositoryImpl
    }

    @Provides
    fun provideProfileUseCase(profileRepository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(profileRepository)
    }

    @Provides
    fun provideExpenseUseCase(expenseRepository: ExpenseRepository): ExpenseUseCase {
        return ExpenseUseCase(expenseRepository) // Assuming ExpenseUseCase is similar to ProfileUseCase
    }

    @Provides
    fun provideInvestmentDao(database: ExpenseDatabase) = database.getInvestmentDao()

    @Provides
    fun provideInvestmentRepository(investmentDao: InvestmentDao): InvestmentRepository {
        return InvestmentRepositoryImpl(investmentDao) // Assuming InvestmentRepositoryImpl is similar to other repositories
    }

    @Provides
    fun provideInvestmentUseCase(investmentRepository: InvestmentRepository): InvestmentUseCase {
        return InvestmentUseCase(investmentRepository) // Assuming InvestmentUseCase is similar to other use cases
    }
}

