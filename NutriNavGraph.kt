package com.example.nutrired.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrired.ui.donation.DonationViewModel
import com.example.nutrired.ui.donation.RegisterDonationScreen
import com.example.nutrired.ui.donation.WindowSizeClass
import com.example.nutrired.ui.inventory.InventoryScreen
import com.example.nutrired.ui.inventory.InventoryViewModel

@Composable
fun NutriNavGraph(viewModel: DonationViewModel, inventoryViewModel: InventoryViewModel, windowSize: WindowSizeClass) {
    val navController = rememberNavController()
    val items = listOf(Screen.Register, Screen.Inventory)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Register.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Register.route) {
                RegisterDonationScreen(viewModel = viewModel, windowSize = windowSize)
            }
            composable(Screen.Inventory.route) {
                InventoryScreen(viewModel = inventoryViewModel)
            }
        }
    }
}
