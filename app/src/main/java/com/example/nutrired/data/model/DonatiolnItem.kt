package com.example.nutrired.data.model

import java.util.UUID

data class DonationItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: String,
    val isUrgent: Boolean,
    val scheduledTime: String
)