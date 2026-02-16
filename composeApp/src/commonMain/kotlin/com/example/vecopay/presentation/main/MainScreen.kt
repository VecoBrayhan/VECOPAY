package com.example.vecopay.presentation.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.vecopay.presentation.navigation.tabs.*

/**
 * Pantalla Principal con navegación por tabs
 * Esta es la pantalla que se muestra después de un login exitoso
 *
 * Contiene:
 * - Bottom Navigation Bar con 4 tabs
 * - HomeTab, AccountsTab, HistoryTab, DebtsTab
 */
class MainScreen : Screen {

    // Key único para evitar problemas de lifecycle
    override val key = "main_screen"

    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(AccountsTab)
                        TabNavigationItem(HistoryTab)
                        TabNavigationItem(DebtsTab)
                    }
                }
            ) { paddingValues ->
                CurrentTab()
            }
        }
    }
}

/**
 * Componente para cada item del bottom navigation
 */
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current.key == tab.key

    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter = painter,
                    contentDescription = tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title,
                style = MaterialTheme.typography.labelSmall
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}