package com.example.nutrired.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Register : Screen("register", "Registrar", Icons.Default.Create)
    object Inventory : Screen("inventory", "Inventario", Icons.Default.List)
}
