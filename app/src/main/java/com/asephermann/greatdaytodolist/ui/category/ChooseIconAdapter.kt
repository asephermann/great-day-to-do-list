package com.asephermann.greatdaytodolist.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.CategoryIcon


class ChooseIconAdapter (private val listIcon:ArrayList<CategoryIcon>) : RecyclerView.Adapter<ChooseIconAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val (icon) = listIcon[position]
        holder.icon.setImageResource(icon)
        holder.textIconId.text = (position+1).toString()
    }

    override fun getItemCount(): Int  = listIcon.size

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var icon: ImageView = itemView.findViewById(R.id.imageView4)
        var textIconId : TextView = itemView.findViewById(R.id.textIconId)

        override fun onClick(v: View?) {
        }

    }
}
