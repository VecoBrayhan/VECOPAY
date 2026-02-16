package com.example.vecopay

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.vecopay.di.AppModule
import com.example.vecopay.presentation.auth.LoginScreen

/**
 * Punto de entrada principal de la aplicación
 *
 * Inicializa:
 * - El tema de la aplicación (MaterialTheme)
 * - El navegador de Voyager
 * - La pantalla inicial (LoginScreen)
 */
@Composable
fun App() {
    MaterialTheme {
        // Inicializamos el ViewModel usando el AppModule
        val authViewModel = remember { AppModule.provideAuthViewModel() }

        // Navigator de Voyager con transiciones suaves
        Navigator(LoginScreen(authViewModel)) { navigator ->
            SlideTransition(navigator)
        }
    }
}