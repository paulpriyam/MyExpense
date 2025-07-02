package com.example.myexpense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myexpense.dao.ProfileDao
import com.example.myexpense.entity.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    // Add other DAOs here as needed
}