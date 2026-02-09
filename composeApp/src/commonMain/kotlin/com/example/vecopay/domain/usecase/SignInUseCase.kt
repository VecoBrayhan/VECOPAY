package com.example.vecopay.domain.usecase

import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.User
import com.example.vecopay.domain.repository.UserRepository

class SignInUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) {
            return Result.Error(Exception("Email is required"), "Email cannot be empty")
        }

        if (password.isBlank()) {
            return Result.Error(Exception("Password is required"), "Password cannot be empty")
        }

        return userRepository.signIn(email, password)
    }
}