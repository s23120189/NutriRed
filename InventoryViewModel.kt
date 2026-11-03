package com.example.nutrired.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrired.data.model.DonationItem
import com.example.nutrired.data.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class InventoryViewModel(
    private val repository: DonationRepository = DonationRepository.instance
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _onlyUrgent = MutableStateFlow(false)
    val onlyUrgent: StateFlow<Boolean> = _onlyUrgent.asStateFlow()

    // Lista ya filtrada: se recalcula sola cuando cambian los datos o los filtros
    val items: StateFlow<List<DonationItem>> = combine(
        repository.getLocalDonations(), _searchQuery, _onlyUrgent
    ) { all, query, urgent ->
        all.filter { item ->
            (query.isBlank() || item.name.contains(query, ignoreCase = true)) &&
                    (!urgent || item.isUrgent)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onSearchChange(text: String) { _searchQuery.value = text }
    fun onUrgentToggle() { _onlyUrgent.value = !_onlyUrgent.value }
    fun clearFilters() { _searchQuery.value = ""; _onlyUrgent.value = false }
}