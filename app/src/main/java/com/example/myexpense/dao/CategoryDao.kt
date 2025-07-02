package com.example.myexpense.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myexpense.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE userId = :userId")
    suspend fun getCategoriesByUserId(userId: String): List<CategoryEntity>
    
    @Query("SELECT * FROM category WHERE name = :name AND userId = :userId")
    suspend fun getCategoryByNameAndUserId(name: String, userId: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}
