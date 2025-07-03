package com.example.myexpense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myexpense.dao.CategoryDao
import com.example.myexpense.dao.ExpenseDao
import com.example.myexpense.dao.InvestmentDao
import com.example.myexpense.dao.ProfileDao
import com.example.myexpense.entity.CategoryEntity
import com.example.myexpense.entity.ExpenseEntity
import com.example.myexpense.entity.InvestmentEntity
import com.example.myexpense.entity.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        ExpenseEntity::class,
        CategoryEntity::class,
        InvestmentEntity::class  // Adding InvestmentEntity to entities list
    ],
    version = 3,  // Updating version number to 3
    exportSchema = false
)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao

    abstract fun getInvestmentDao(): InvestmentDao

    companion object {
        // Migration from 1 to 2: Adding ExpenseEntity and CategoryEntity tables
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the expense table
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `expense` (
                        `id` TEXT NOT NULL,
                        `amount` REAL NOT NULL,
                        `description` TEXT NOT NULL,
                        `date` INTEGER NOT NULL,
                        `category` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`userId`) REFERENCES `profile`(`userId`) 
                        ON DELETE CASCADE
                    )
                    """
                )

                // Create the category table
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `category` (
                        `id` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the investment table
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `investment` (
                        `investmentId` TEXT NOT NULL,
                        `investmentName` TEXT NOT NULL,
                        `investmentAmount` REAL NOT NULL,
                        `investmentDate` TEXT NOT NULL,
                        `investmentType` TEXT NOT NULL,
                        `investmentDescription` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        `sipType` TEXT,
                        `sipAmount` REAL,
                        `fdType` TEXT,
                        `fdAmount` REAL,
                        `fdDuration` TEXT,
                        `fdInterest` REAL,
                        `fdMaturityDate` TEXT,
                        `fdMaturityAmount` REAL,
                        `sharePrice` REAL,
                        `shareQuantity` REAL,
                        PRIMARY KEY(`investmentId`),
                        FOREIGN KEY(`userId`) REFERENCES `profile`(`userId`) 
                        ON DELETE CASCADE
                    )
                    """
                )
            }
        }
    }
}
