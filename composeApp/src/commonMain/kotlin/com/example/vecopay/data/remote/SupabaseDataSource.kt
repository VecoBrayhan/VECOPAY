package com.example.vecopay.data.remote

import com.example.vecopay.core.network.SupabaseClientProvider
import com.example.vecopay.data.remote.dto.UserDto
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseDataSource {

    private val supabase = SupabaseClientProvider.client

    suspend fun signUp(email: String, password: String): UserDto? {
        return try {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            // Después del registro, obtener el usuario actual
            val currentUser = supabase.auth.currentUserOrNull()
            currentUser?.let { user ->
                UserDto(
                    id = user.id,
                    email = user.email ?: email
                )
            }
        } catch (e: Exception) {
            println("Error en signUp: ${e.message}")
            null
        }
    }

    suspend fun signIn(email: String, password: String): UserDto? {
        return try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            // Después del login, obtener el usuario actual
            val currentUser = supabase.auth.currentUserOrNull()
            currentUser?.let { user ->
                UserDto(
                    id = user.id,
                    email = user.email ?: email
                )
            }
        } catch (e: Exception) {
            println("Error en signIn: ${e.message}")
            null
        }
    }

    suspend fun signOut() {
        try {
            supabase.auth.signOut()
        } catch (e: Exception) {
            println("Error en signOut: ${e.message}")
        }
    }

    suspend fun getCurrentUser(): UserDto? {
        return try {
            supabase.auth.currentUserOrNull()?.let { user ->
                UserDto(
                    id = user.id,
                    email = user.email ?: ""
                )
            }
        } catch (e: Exception) {
            println("Error en getCurrentUser: ${e.message}")
            null
        }
    }

    fun observeAuthState(): Flow<UserDto?> = flow {
        try {
            supabase.auth.sessionStatus.collect { session ->
                val user = supabase.auth.currentUserOrNull()
                emit(user?.let {
                    UserDto(
                        id = it.id,
                        email = it.email ?: ""
                    )
                })
            }
        } catch (e: Exception) {
            println("Error en observeAuthState: ${e.message}")
            emit(null)
        }
    }
}