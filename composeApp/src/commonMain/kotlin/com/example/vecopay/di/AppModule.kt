package com.example.vecopay.di

import com.example.vecopay.data.remote.SupabaseDataSource
import com.example.vecopay.data.repository.UserRepositoryImpl
import com.example.vecopay.domain.repository.UserRepository
import com.example.vecopay.domain.usecase.SignInUseCase
import com.example.vecopay.domain.usecase.SignUpUseCase
import com.example.vecopay.presentation.auth.AuthViewModel

object AppModule {

    // Data Layer
    private val supabaseDataSource: SupabaseDataSource by lazy {
        SupabaseDataSource()
    }

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(supabaseDataSource)
    }

    // Domain Layer
    private val signInUseCase: SignInUseCase by lazy {
        SignInUseCase(userRepository)
    }

    private val signUpUseCase: SignUpUseCase by lazy {
        SignUpUseCase(userRepository)
    }

    // Presentation Layer
    fun provideAuthViewModel(): AuthViewModel {
        return AuthViewModel(
            signInUseCase = signInUseCase,
            signUpUseCase = signUpUseCase
        )
    }
}