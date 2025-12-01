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
fun RegistrationScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit // Este ya no lo usaremos realmente, pero lo dejamos por compatibilidad
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            // NUEVO CASO: Registro exitoso
            is LoginUiState.RegistrationSuccess -> {
                Toast.makeText(context, "¡Registro exitoso! Por favor inicia sesión.", Toast.LENGTH_LONG).show()
                viewModel.clearError() // Limpiamos el estado
                onNavigateToLogin() // <--- REDIRIGE AL LOGIN
            }
            is LoginUiState.Error -> {
                val error = (uiState as LoginUiState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // ... (El resto del diseño UI de la pantalla se mantiene IDÉNTICO)
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crea tu cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it; viewModel.clearError() },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; viewModel.clearError() },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; viewModel.clearError() },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (fullName.isNotBlank() && email.isNotBlank() && password.length >= 6) {
                    viewModel.register(fullName, email, password)
                } else {
                    Toast.makeText(context, "Completa los campos (Pass min 6)", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUiState.Loading
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Registrarse")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión aquí")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    // com.example.perfulandia.ui.theme.PerfulandiaTheme {
    //    RegistrationScreen(onNavigateToLogin = {}, onRegistrationSuccess = {})
    // }
}