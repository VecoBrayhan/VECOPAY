package com.example.vecopay.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person // Necesario para el icono del Tab
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

// CAMBIO 1: Usamos 'object' y hereda de 'Tab' en lugar de 'Screen'
object ProfileTab : Tab {

    // CAMBIO 2: Implementamos las opciones del Tab (Icono y Título)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Perfil"
            val icon = rememberVectorPainter(Icons.Default.Person)

            return remember {
                TabOptions(
                    index = 99u, // Un índice alto para que no estorbe
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        // Obtenemos el navegador raíz para poder hacer "pop" (volver atrás)
        // Nota: Al ser un Tab, el 'pop' podría comportarse distinto dependiendo de tu configuración en MainScreen
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navigator.pop() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }

                        Text(
                            text = "Perfil y Configuración",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⚙️ Configuración",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Pantalla de Perfil/Configuración",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}