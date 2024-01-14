package com.panda.todopanda.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panda.todopanda.data.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class TasksListViewModel : ViewModel() {
    private val webService = Api.tasksWebService

    public val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks()
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks
        }
    }


    fun add(task: Task) {}
    fun edit(task: Task) {}
    fun remove(task: Task) {}
}