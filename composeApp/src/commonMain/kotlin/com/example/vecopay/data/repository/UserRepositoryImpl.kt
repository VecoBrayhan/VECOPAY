package com.example.vecopay.data.repository

import com.example.vecopay.core.util.Result
import com.example.vecopay.data.remote.SupabaseDataSource
import com.example.vecopay.data.remote.dto.toDomain
import com.example.vecopay.domain.model.User
import com.example.vecopay.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val remoteDataSource: SupabaseDataSource
) : UserRepository {

    override suspend fun signUp(email: String, password: String): Result<User> {
        return try {
            val userDto = remoteDataSource.signUp(email, password)
            if (userDto != null) {
                Result.Success(userDto.toDomain())
            } else {
                Result.Error(Exception("Sign up failed"), "Could not create user")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val userDto = remoteDataSource.signIn(email, password)
            if (userDto != null) {
                Result.Success(userDto.toDomain())
            } else {
                Result.Error(Exception("Sign in failed"), "Invalid credentials")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            remoteDataSource.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val userDto = remoteDataSource.getCurrentUser()
            Result.Success(userDto?.toDomain())
        } catch (e: Exception) {
            Result.Error(e, e.message ?: "Unknown error occurred")
        }
    }

    override fun observeAuthState(): Flow<User?> {
        return remoteDataSource.observeAuthState().map { it?.toDomain() }
    }
}