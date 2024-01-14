package com.panda.todopanda.data
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class User(
    @SerialName("email")
    val email: String,

    @SerialName("full_name")
    val name: String,

    @SerialName("avatar_medium")
    val avatar: String? = null
)