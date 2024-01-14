package com.panda.todopanda.list
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.io.Serializable as JavaSerializable

@Serializable
data class Task(
    @SerialName("id")
    val id: String,

    @SerialName("content")
    val title: String,

    @SerialName("description")
    val description: String
) : JavaSerializable