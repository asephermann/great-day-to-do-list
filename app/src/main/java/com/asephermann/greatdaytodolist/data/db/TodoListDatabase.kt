package com.asephermann.greatdaytodolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.asephermann.greatdaytodolist.data.dao.CategoryDao
import com.asephermann.greatdaytodolist.data.dao.TaskDao
import com.asephermann.greatdaytodolist.data.model.Category
import com.asephermann.greatdaytodolist.data.model.Task
import com.asephermann.greatdaytodolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Category::class, Task::class], version = 4)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TodoListDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val categoryDao = database.get().categoryDao()
            val taskDao = database.get().taskDao()

            applicationScope.launch {
                categoryDao.insert(Category(name = "Work", iconId = 6))
                categoryDao.insert(Category(name = "Sport", iconId = 2))
                categoryDao.insert(Category(name = "Meeting", iconId = 3))
                categoryDao.insert(Category(name = "Shopping", iconId = 4))

                taskDao.insert(
                    Task(
                        title = "Email Check",
                        categoryName = "Work",
                        iconId = 6,
                        desc = "check email on last 7 days",
                        dateStart = "May-19-2022",
                        dateEnds = "May-20-2022",
                        isDone = false
                    )
                )

                taskDao.insert(
                    Task(
                        title = "Weekly meeting",
                        categoryName = "Meeting",
                        iconId = 3,
                        desc = "meeting at Friday, May 20 2022",
                        dateStart = "May-19-2022",
                        dateEnds = "May-20-2022",
                        isDone = true
                    )
                )
                taskDao.insert(
                    Task(
                        title = "Team Discussion",
                        categoryName = "Meeting",
                        iconId = 3,
                        desc = "meeting at Friday, May 21 2022",
                        dateStart = "May-20-2022",
                        dateEnds = "May-21-2022",
                        isDone = false
                    )
                )
            }
        }
    }
}