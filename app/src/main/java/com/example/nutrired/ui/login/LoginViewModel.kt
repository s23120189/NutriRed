package com.example.nutrired.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.nutrired.data.model.LoginUiState

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun cambiarUsuario(usuario: String) {
        _uiState.update {
            it.copy(usuario = usuario, mensaje = "")
        }
    }

    fun cambiarPassword(password: String) {
        _uiState.update {
            it.copy(password = password, mensaje = "")
        }
    }

    fun cambiarRol(rol: Rol) {
        _uiState.update {
            it.copy(rolSeleccionado = rol, mensaje = "")
        }
    }

    fun cambiarVisibilidadPassword() {
        _uiState.update {
            it.copy(mostrarPassword = !it.mostrarPassword)
        }
    }

    fun iniciarSesion() {
        val estado = _uiState.value
        val usuario = estado.usuario.trim()
        val password = estado.password

        if (usuario.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(mensaje = "Completa todos los campos.")
            }
            return
        }

        val accesoValido = when (estado.rolSeleccionado) {
            Rol.ADMIN ->
                usuario == "admin" && password == "admin123"

            Rol.CAJERO ->
                usuario == "cajero" && password == "cajero123"

            Rol.ASISTENTE ->
                usuario == "asistente" && password == "asistente123"
        }

        if (accesoValido) {
            _uiState.update {
                it.copy(
                    sesionIniciada = true,
                    mensaje = ""
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    mensaje = "Usuario, contraseña o rol incorrectos."
                )
            }
        }
    }

    fun cerrarSesion() {
        _uiState.value = LoginUiState()
    }
}

enum class Rol(val titulo: String) {
    ADMIN("Administrador"),
    CAJERO("Cajero"),
    ASISTENTE("Asistente")
}

