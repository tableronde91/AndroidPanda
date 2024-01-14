package com.panda.todopanda.list

interface TaskListListener {
    fun onClickDelete(task: Task)
    fun onClickEdit(task: Task)
}

