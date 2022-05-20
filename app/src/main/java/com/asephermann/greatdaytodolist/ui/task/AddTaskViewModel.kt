package com.asephermann.greatdaytodolist.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.asephermann.greatdaytodolist.data.dao.TaskDao
import com.asephermann.greatdaytodolist.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    val openTasks = taskDao.getOpenTaskList().asLiveData()
}