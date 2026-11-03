package com.example.nutrired.ui.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    val items by viewModel.items.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val onlyUrgent by viewModel.onlyUrgent.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onSearchChange,
            label = { Text("Buscar por nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        FilterChip(
            selected = onlyUrgent,
            onClick = viewModel::onUrgentToggle,
            label = { Text("Solo urgentes") }
        )

        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay donaciones que coincidan")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items, key = { it.id }) { item ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(item.name, style = MaterialTheme.typography.bodyLarge)
                            Text("Tipo: ${item.type} | Hora: ${item.scheduledTime}",
                                style = MaterialTheme.typography.bodySmall)
                            if (item.isUrgent) {
                                Text("Prioridad: CRÍTICA", color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}