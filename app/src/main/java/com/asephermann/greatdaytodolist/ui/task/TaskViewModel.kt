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
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao,
) : ViewModel() {

    val openTasks = taskDao.getOpenTasks().asLiveData()
    val completedTasks = taskDao.getCompletedTasks().asLiveData()

    fun onTaskSelected(task: Task) {}

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(isDone = isChecked))
    }
}