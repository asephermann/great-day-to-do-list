package com.asephermann.greatdaytodolist.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.Task
import kotlinx.android.synthetic.main.task_checkbox.view.*

class TasksAdapter(private val listener: OnItemClickListener) : ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_checkbox, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.apply {
                this.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                checkBox_task.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckBoxClick(task, checkBox_task.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            itemView.apply {
                task_name.text = task.title
                task_name.paint.isStrikeThruText = task.isDone

                icon_category.setImageResource(
                    when(task.iconId){
                        1 -> R.drawable.ic_image
                        2 -> R.drawable.ic_heart
                        3 -> R.drawable.ic_user
                        4 -> R.drawable.ic_plus
                        5 -> R.drawable.ic_arrow_right
                        else -> R.drawable.ic_work
                    })

                checkBox_task.isChecked = task.isDone
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}