package com.example.nutrired.ui.donation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrired.data.model.DonationItem
import com.example.nutrired.data.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DonationViewModel(
    private val repository: DonationRepository = DonationRepository.instance
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<DonationItem>>(emptyList())
    val uiState: StateFlow<List<DonationItem>> = _uiState.asStateFlow()

    init {
        // Observación asíncrona del flujo de datos del Repositorio
        viewModelScope.launch {
            repository.getLocalDonations().collect { donations ->
                _uiState.value = donations
            }
        }
    }

    fun registerDonation(name: String, type: String, isUrgent: Boolean, time: String) {
        viewModelScope.launch {
            val newDonation = DonationItem(
                name = name,
                type = type,
                isUrgent = isUrgent,
                scheduledTime = time
            )
            repository.saveDonationLocally(newDonation)
        }
    }
}
