package com.example.vecopay.domain.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): Result<User?>
    fun observeAuthState(): Flow<User?>
}