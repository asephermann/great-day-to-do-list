package com.asephermann.greatdaytodolist.ui.category

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.Category
import kotlinx.android.synthetic.main.card_category.view.*

class CategoriesAdapter : ListAdapter<Category, CategoriesAdapter.CategoriesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.apply {
                text_category_name.text = category.name

                card_category.background = when(category.iconId) {
                    1 -> resources.getDrawable(R.drawable.bg_category_orange)
                    2 -> resources.getDrawable(R.drawable.bg_category_blue)
                    3 -> resources.getDrawable(R.drawable.bg_category_green)
                    4 -> resources.getDrawable(R.drawable.bg_category_red)
                    5 -> resources.getDrawable(R.drawable.bg_category_purple)
                    else -> resources.getDrawable(R.drawable.bg_category_yellow)
                }

                category_icon.setImageResource(
                    when(category.iconId){
                        1 -> R.drawable.ic_image
                        2 -> R.drawable.ic_heart
                        3 -> R.drawable.ic_user
                        4 -> R.drawable.ic_plus
                        5 -> R.drawable.ic_arrow_right
                        else -> R.drawable.ic_work
                    })

                line.setBackgroundColor(0x00000000)

                card_category.setOnClickListener {
                    line.setBackgroundColor(0x77000000)

                    Handler(Looper.getMainLooper()).postDelayed({
                        line.setBackgroundColor(0x00000000)
                    }, 1000)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }
}