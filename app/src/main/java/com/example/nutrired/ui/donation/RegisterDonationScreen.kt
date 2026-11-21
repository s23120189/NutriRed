package com.example.nutrired.ui.donation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nutrired.R
import com.example.nutrired.data.model.DonationItem


enum class WindowSizeClass { COMPACT, EXPANDED }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDonationScreen(viewModel: DonationViewModel, windowSize: WindowSizeClass) {
    val donations by viewModel.uiState.collectAsState()
    var foodName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Perecedero") }
    var isUrgent by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("12:00 PM") }
    Scaffold(

        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.app_name)) }) }
    ) { innerPadding ->

        // DISEÑO RESPONSIVO: Modifica el Layout según las dimensiones físicas del dispositivo
        if (windowSize == WindowSizeClass.EXPANDED) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                FormContent(
                    modifier = Modifier.weight(1f),
                    foodName = foodName, onNameChange = { foodName = it },
                    selectedType = selectedType, onTypeChange = { selectedType = it },
                    isUrgent = isUrgent, onUrgentChange = { isUrgent = it },
                    selectedTime = selectedTime, onTimeChange = { selectedTime = it },
                    onSave = {
                        viewModel.registerDonation(foodName, selectedType, isUrgent, selectedTime)
                        foodName = ""
                    }
                )
                ListContent(
                    modifier = Modifier.weight(1f),
                    donations = donations
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormContent(
                    modifier = Modifier.fillMaxWidth(),
                    foodName = foodName, onNameChange = { foodName = it },
                    selectedType = selectedType, onTypeChange = { selectedType = it },
                    isUrgent = isUrgent, onUrgentChange = { isUrgent = it },
                    selectedTime = selectedTime, onTimeChange = { selectedTime = it },
                    onSave = {
                        viewModel.registerDonation(foodName, selectedType, isUrgent, selectedTime)
                        foodName = ""
                    }
                )
                ListContent(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    donations = donations
                )
            }
        }
    }
}

@Composable
fun FormContent(
    modifier: Modifier = Modifier,
    foodName: String, onNameChange: (String) -> Unit,
    selectedType: String, onTypeChange: (String) -> Unit,
    isUrgent: Boolean, onUrgentChange: (Boolean) -> Unit,
    selectedTime: String, onTimeChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Caja de Texto (TextField)
        OutlinedTextField(
            value = foodName,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.hint_food_name)) },
            modifier = Modifier.fillMaxWidth()
        )
        // Botones de Radio (RadioButtons)
        Text(stringResource(R.string.label_type), style = MaterialTheme.typography.titleSmall)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = (selectedType == "Perecedero"), onClick = { onTypeChange("Perecedero") })
            Text("Perecedero", modifier = Modifier.padding(end = 16.dp))
            RadioButton(selected = (selectedType == "No Perecedero"), onClick = { onTypeChange("No Perecedero") })
            Text("No Perecedero")
        }
        // Casilla de Verificación (Checkbox)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isUrgent, onCheckedChange = onUrgentChange)
            Text(stringResource(R.string.label_urgent))
        }

        // Componente Funcional de Tiempo (TimePicker Emulado por Evento Dinámico)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { onTimeChange("03:45 PM") }) {
                Text(stringResource(R.string.btn_pick_time))
            }
            Text("Hora fijada: $selectedTime", style = MaterialTheme.typography.bodyMedium)
        }

        // Botón de Acción
        Button(
            onClick = onSave,
            enabled = foodName.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_save))
        }
    }
}

@Composable
fun ListContent(modifier: Modifier = Modifier, donations: List<DonationItem>) {
    Column(modifier = modifier) {
        Text("Cola de Envío Local (Offline DB)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        // Lista Eficiente (LazyColumn)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(donations, key = { it.id }) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Tipo: ${item.type} | Hora: ${item.scheduledTime}", style = MaterialTheme.typography.bodySmall)
                        if (item.isUrgent) {
                            Text(text = "Prioridad: CRÍTICA", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}