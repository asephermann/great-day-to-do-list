package com.asephermann.greatdaytodolist.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.asephermann.greatdaytodolist.data.dao.CategoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    categoryDao: CategoryDao
) : ViewModel() {

    val categories = categoryDao.getAllCategories().asLiveData()
    val categoriesName = categoryDao.getAllCategoriesName().asLiveData()

    private val searchQuery = MutableStateFlow("")

    private val categoryFlow = searchQuery.flatMapLatest {
        categoryDao.getCategoryByName(it)
    }

    val category = categoryFlow.asLiveData()
}