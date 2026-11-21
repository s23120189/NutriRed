package com.example.nutrired.data.model

import com.example.nutrired.ui.login.Rol

data class LoginUiState (
    val usuario: String = "",
    val password: String = "",
    val mostrarPassword: Boolean = false,
    val rolSeleccionado: Rol = Rol.ADMIN,
    val mensaje: String = "",
    val sesionIniciada: Boolean = false
)
