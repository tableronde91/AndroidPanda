package com.panda.todopanda.list
import java.io.Serializable

data class Task(val id: String, val title: String, val description: String) : Serializable