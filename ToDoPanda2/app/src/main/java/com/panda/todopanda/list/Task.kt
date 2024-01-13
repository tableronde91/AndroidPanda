package com.panda.todopanda.list

data class Task(
    val id: String,
    val title: String,
    val description: String = ""
)