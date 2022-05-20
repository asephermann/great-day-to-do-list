package com.asephermann.greatdaytodolist.data.dao

import androidx.room.*
import com.asephermann.greatdaytodolist.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE categoryName =:categoryName")
    fun getTasksByCategoryName(categoryName: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE isDone=0")
    fun getOpenTasks(): Flow<List<Task>>

    @Query("SELECT title FROM task WHERE isDone=0")
    fun getOpenTaskList(): Flow<List<String>>

    @Query("SELECT * FROM task WHERE isDone=1")
    fun getCompletedTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}