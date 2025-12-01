package com.example.perfulandia.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    // Inyectamos el ViewModel correcto
    viewModel: LoginViewModel = viewModel(),
    // Recibimos las acciones de navegación
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    // Escuchamos el estado de Supabase (Cargando, Éxito, Error)
    val uiState by viewModel.uiState.collectAsState()

    // Estado local para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Reaccionar al estado del login
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                onLoginSuccess() // Navegar al Home
                viewModel.clearError() // Limpiar estado
            }
            is LoginUiState.Error -> {
                val error = (uiState as LoginUiState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
            else -> {} // Idle o Loading no hacen nada aquí
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.clearError() // Limpia errores al escribir
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.clearError()
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Iniciar Sesión
        Button(
            onClick = {
                // AQUÍ conectamos con la base de datos
                viewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUiState.Loading // Deshabilitar si carga
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Iniciar sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir al Registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Preview dummy
    // com.example.perfulandia.ui.theme.PerfulandiaTheme {
    //     LoginScreen(onNavigateToRegister = {}, onLoginSuccess = {})
    // }
}