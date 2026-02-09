package com.example.vecopay.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String? = null,
    val createdAt: String? = null
)