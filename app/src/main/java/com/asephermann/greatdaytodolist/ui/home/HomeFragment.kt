package com.asephermann.greatdaytodolist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.Task
import com.asephermann.greatdaytodolist.ui.category.AddCategoryFragment
import com.asephermann.greatdaytodolist.ui.category.CategoriesAdapter
import com.asephermann.greatdaytodolist.ui.category.CategoryViewModel
import com.asephermann.greatdaytodolist.ui.task.AddTaskFragment
import com.asephermann.greatdaytodolist.ui.task.TaskViewModel
import com.asephermann.greatdaytodolist.ui.task.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_radiobutton.*
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener, TasksAdapter.OnItemClickListener {

    private val categoryViewModel : CategoryViewModel by viewModels()
    private val taskViewModel : TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesAdapter = CategoriesAdapter()
        val tasksAdapter = TasksAdapter(this)

        rv_category.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = categoriesAdapter
        }
        rv_task.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = tasksAdapter
        }

        categoryViewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
            categoriesAdapter.notifyDataSetChanged()
        }

        taskViewModel.openTasks.observe(viewLifecycleOwner) {
            tasksAdapter.submitList(it)
            tasksAdapter.notifyDataSetChanged()
        }

        buttonAddCategory.setOnClickListener(this)
        buttonAddTask.setOnClickListener(this)

        radioGroup1.setOnCheckedChangeListener { _, _ ->
            if(radio_task_list.isChecked){
                taskViewModel.openTasks.observe(viewLifecycleOwner) {
                    tasksAdapter.submitList(it)
                }
            }else{
                taskViewModel.completedTasks.observe(viewLifecycleOwner) {
                    tasksAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClick(task: Task) {
        taskViewModel.onTaskSelected(task)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        taskViewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onClick(v: View?) {
        val mFragmentManager = parentFragmentManager
        when (v?.id){
            R.id.buttonAddCategory -> {
                requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.md_light_blue_A800)
                val mAddCategoryFragment = AddCategoryFragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mAddCategoryFragment, AddCategoryFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            R.id.buttonAddTask -> {
                requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.md_light_blue_A800)
                val mAddTaskFragment = AddTaskFragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mAddTaskFragment, AddTaskFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}