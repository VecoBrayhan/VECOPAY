package com.example.vecopay.domain.usecase

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.User
import com.example.vecopay.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Aquí puedes agregar validaciones de negocio
        if (email.isBlank()) {
            return Result.Error(Exception("Email is required"), "Email cannot be empty")
        }

        if (password.length < 6) {
            return Result.Error(Exception("Password too short"), "Password must be at least 6 characters")
        }

        return userRepository.signUp(email, password)
    }
}