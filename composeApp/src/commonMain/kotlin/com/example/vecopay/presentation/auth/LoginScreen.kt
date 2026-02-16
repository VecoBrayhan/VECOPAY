package com.example.vecopay.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.vecopay.di.AppModule
import com.example.vecopay.presentation.main.MainScreen

/**
 * Pantalla de Login
 * Esta es la pantalla inicial de la aplicación
 */
class LoginScreen(
    private val viewModel: AuthViewModel
) : Screen {

    override val key = "login_screen"

    @Composable
    override fun Content() {
        LoginContent(viewModel)
    }
}

@Composable
private fun LoginContent(viewModel: AuthViewModel) {
    val navigator = LocalNavigator.currentOrThrow

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    // Navegar a MainScreen cuando el login sea exitoso
    // Usamos DisposableEffect para limpiar correctamente
    DisposableEffect(authState.isSuccess) {
        if (authState.isSuccess && authState.user != null) {
            navigator.replace(MainScreen())
        }
        onDispose { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (authState.error != null) {
            Text(
                text = authState.error ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { viewModel.signIn(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading
        ) {
            if (authState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { /* TODO: Navegar a SignUp */ },
            enabled = !authState.isLoading
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}