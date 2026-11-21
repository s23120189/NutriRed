package com.example.nutrired.data.repository

import com.example.nutrired.data.model.DonationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DonationRepository {
    // Simulación en memoria de la base de datos persistente (Room)
    private val _localDatabase = MutableStateFlow<List<DonationItem>>(emptyList())

    // Expone el flujo de datos para que el ViewModel lo observe en tiempo real
    fun getLocalDonations(): Flow<List<DonationItem>> = _localDatabase.asStateFlow()

    suspend fun saveDonationLocally(donation: DonationItem): Boolean {
        val currentList = _localDatabase.value.toMutableList()
        currentList.add(donation)
        _localDatabase.value = currentList
        return true // Simulación de inserción exitosa
    }

    // Instancia única: así el formulario y el inventario leen la MISMA lista
    companion object {
        val instance: DonationRepository by lazy { DonationRepository() }
    }
}
