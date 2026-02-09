package com.example.vecopay

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.vecopay.di.AppModule
import com.example.vecopay.presentation.auth.LoginScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Inicializamos el ViewModel usando el AppModule
        // Usamos 'remember' para mantener la instancia mientras la UI esté activa
        val authViewModel = remember { AppModule.provideAuthViewModel() }

        // Mostramos la pantalla de Login pasándole el ViewModel necesario
        LoginScreen(
            viewModel = authViewModel,
            onNavigateToSignUp = {
                // Aquí podrías agregar lógica para cambiar de pantalla a Registro
                println("Navegar a registro")
            },
            onLoginSuccess = {
                // Aquí podrías agregar lógica para navegar al Home después del login
                println("Login exitoso")
            }
        )
    }
}