package com.example.nutrired.ui.login


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val estado by viewModel.uiState.collectAsState()
    val colorPrincipal = MaterialTheme.colorScheme.primary

    if (estado.sesionIniciada) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = colorPrincipal
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Bienvenido, ${estado.usuario.trim()}!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Acceso: ${estado.rolSeleccionado.titulo}",
                modifier = Modifier.padding(12.dp)
            )

            Text("Sistema de inventario")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { viewModel.cerrarSesion() }) {
                Text("Cerrar sesión")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = "Inventario",
                modifier = Modifier.size(64.dp),
                tint = colorPrincipal
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sistema de inventario",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text("Ingresa tus credenciales")

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = estado.usuario,
                onValueChange = viewModel::cambiarUsuario,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Usuario") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::cambiarPassword,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation =
                    if (estado.mostrarPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        text = if (estado.mostrarPassword) "Ocultar" else "Ver",
                        modifier = Modifier
                            .clickable {
                                viewModel.cambiarVisibilidadPassword()
                            }
                            .padding(8.dp),
                        color = colorPrincipal
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selecciona tu tipo de acceso",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Rol.values().forEach { rol ->
                val seleccionado = estado.rolSeleccionado == rol

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { viewModel.cambiarRol(rol) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (seleccionado)
                        colorPrincipal.copy(alpha = 0.10f)
                    else
                        MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        width = if (seleccionado) 2.dp else 1.dp,
                        color = if (seleccionado)
                            colorPrincipal
                        else
                            MaterialTheme.colorScheme.outline
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = seleccionado,
                            onClick = { viewModel.cambiarRol(rol) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = rol.titulo,
                            fontWeight = if (seleccionado)
                                FontWeight.Bold
                            else
                                FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.iniciarSesion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Iniciar sesión", fontSize = 16.sp)
            }

            if (estado.mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = estado.mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}