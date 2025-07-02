package com.example.myexpense.di

import android.content.Context
import androidx.room.Room
import com.example.myexpense.db.ExpenseDatabase
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
        ).build()
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        // Provides the application context
        return context
    }

    @Provides
    fun provideProfileDao(database: ExpenseDatabase) = database.profileDao()


}