package com.asephermann.greatdaytodolist.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asephermann.greatdaytodolist.data.dao.CategoryDao
import com.asephermann.greatdaytodolist.data.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryDao: CategoryDao
) : ViewModel() {

    fun createCategory(category: Category) = viewModelScope.launch {
        categoryDao.insert(category)
    }
}