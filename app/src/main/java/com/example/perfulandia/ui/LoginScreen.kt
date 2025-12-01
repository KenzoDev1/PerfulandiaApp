package com.example.perfulandia.ui.auth

// Asegúrate de tener esta importación
import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- 1. Estados para los mensajes de error ---
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // --- 2. Lógica de Validación ---
    fun validateForm(): Boolean {
        var isValid = true

        // Validación de Email
        if (email.isBlank()) {
            emailError = "El correo no puede estar vacío"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Formato de correo incorrecto"
            isValid = false
        } else {
            emailError = null
        }

        // Validación de Contraseña
        if (password.isBlank()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar sesión"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 3. Campos de Texto Actualizados ---

        // Campo de texto para el email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null // Limpia el error al escribir
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null, // <-- Indica si hay error
            supportingText = { // <-- Muestra el mensaje de error
                if (emailError != null) {
                    Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null // Limpia el error al escribir
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null, // <-- Indica si hay error
            supportingText = { // <-- Muestra el mensaje de error
                if (passwordError != null) {
                    Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 4. Botón de Iniciar sesión Actualizado ---
        Button(
            onClick = {
                if (validateForm()) {
                    // Formulario VÁLIDO
                    println("Datos: : Email: $email")
                    // AQUI ira la lógica para iniciar sesión
                } else {
                    // Formulario INVÁLIDO
                    println("El formulario contiene errores")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    com.example.perfulandia.ui.theme.PerfulandiaTheme {
        LoginScreen()
    }
}