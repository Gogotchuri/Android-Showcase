package com.example.androidshowcase.ui.insidelibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidshowcase.R
import com.example.androidshowcase.database.entities.Component

class ComponentsRecyclerAdapter : RecyclerView.Adapter<ComponentsRecyclerAdapter.ViewHolder>() {
    private var componentsList = listOf<Component>()
    var itemClickedListener: ((component: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_library_cell, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return componentsList.count()
    }

    fun setData(componentsList: List<Component>) {
        this.componentsList = componentsList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val componentName = itemView.findViewById<TextView>(R.id.library_name_text)

        init {
            itemView.setOnClickListener {
                itemClickedListener?.invoke(componentsList[adapterPosition].name)
            }
        }

        fun bindData(position: Int) {
            componentName.text = componentsList[position].name
        }
    }
}