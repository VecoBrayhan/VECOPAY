package com.example.vecopay.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.vecopay.domain.model.User

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        name = name,
        createdAt = createdAt
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        name = name,
        createdAt = createdAt
    )
}