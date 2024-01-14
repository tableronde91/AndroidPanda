package com.panda.todopanda.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.panda.todopanda.R

class TaskListAdapter(private val listener: TaskListListener) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    var onClickDelete: (Task) -> Unit = {}
    var onEditClick: (Task) -> Unit = {}
    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = currentList[position]
        holder.deleteButton.setOnClickListener { listener.onClickDelete(task) }
        holder.editButton.setOnClickListener { listener.onClickEdit(task) }
        holder.bind(task)
    }

    fun refreshAdapter(newList: List<Task>) {
        currentList = newList

    }
    inner class TaskViewHolder(itemView: View, private val listener: TaskListListener) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.task_title)
        private val descriptionView: TextView = itemView.findViewById(R.id.task_description)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        init {
            deleteButton.setOnClickListener {

            }
            editButton.setOnClickListener(){

            }
        }
        fun bind(task: Task) {
            titleView.text = task.title
            descriptionView.text = task.description
            deleteButton.setOnClickListener { onClickDelete(task) }
        }
    }
}
