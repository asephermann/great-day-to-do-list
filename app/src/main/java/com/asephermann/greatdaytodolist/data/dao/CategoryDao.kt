package com.asephermann.greatdaytodolist.data.dao

import androidx.room.*
import com.asephermann.greatdaytodolist.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT name FROM category")
    fun getAllCategoriesName(): Flow<List<String>>

    @Query("SELECT * FROM category WHERE name=:categoryName")
    fun getCategoryByName(categoryName: String): Flow<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)
}