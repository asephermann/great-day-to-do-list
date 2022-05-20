package com.asephermann.greatdaytodolist.di

import android.app.Application
import androidx.room.Room
import com.asephermann.greatdaytodolist.data.db.TodoListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TodoListDatabase.Callback
    ) = Room.databaseBuilder(app, TodoListDatabase::class.java, "todolist_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideCategoryDao(db: TodoListDatabase) = db.categoryDao()

    @Provides
    fun provideTaskDao(db: TodoListDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope