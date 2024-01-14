package com.panda.todopanda.list
import com.panda.todopanda.list.TasksListViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.panda.todopanda.R
import com.panda.todopanda.data.Api
import com.panda.todopanda.data.UserWebService
import com.panda.todopanda.detail.DetailActivity
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import java.util.UUID

class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2", description = "description 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
    private val adapterListener: TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            taskList = taskList.filter { it.id != task.id }
            adapter.currentList = taskList
            adapter.notifyDataSetChanged()
        }

        override fun onClickEdit(task: Task) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }
    private val adapter = TaskListAdapter(adapterListener)
    private val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newTask = result.data?.getSerializableExtra("task") as Task?
            newTask?.let { task ->
                taskList = taskList.map { if (it.id == task.id) task else it }
                adapter.currentList = taskList
                adapter.notifyDataSetChanged()
            }
        }
    }

    private val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val editedTask = result.data?.getSerializableExtra("task") as Task?
            editedTask?.let { task ->
                taskList = taskList.map { if (it.id == task.id) task else it }
                adapter.currentList = taskList
                adapter.notifyDataSetChanged()
            }
        }
    }
    private val viewModel: TasksListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.tasksStateFlow.collect { newList ->

                adapter.currentList = newList
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            try {
                val response = Api.userWebService.fetchUser()
                val user = response.body()

                if (user != null) {

                    view?.findViewById<TextView>(R.id.userTextView)?.text = user.name
                }
            } catch (e: Exception) {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.currentList = taskList

        adapter.onClickDelete= { task ->
            taskList = taskList.filter { it.id != task.id }
            adapter.currentList = taskList
            adapter.notifyDataSetChanged()
        }

        adapter.onEditClick = { task ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }

        val addButton = view.findViewById<Button>(R.id.floatingActionButton)
        addButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            createTask.launch(intent)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        taskList = emptyList()
    }

}