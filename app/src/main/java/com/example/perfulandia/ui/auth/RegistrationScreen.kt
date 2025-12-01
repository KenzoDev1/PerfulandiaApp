package com.example.perfulandia.ui.auth

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
fun RegistrationScreen() {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- 1. Estados para los mensajes de error ---
    // Si el String es 'null', no hay error. Si tiene texto, hay un error.
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // --- 2. Lógica de Validación ---
    fun validateForm(): Boolean {
        var isValid = true // Asumimos que es válido hasta que se demuestre lo contrario

        // Validación de Nombre
        if (fullName.isBlank()) {
            fullNameError = "El nombre no puede estar vacío"
            isValid = false
        } else {
            fullNameError = null
        }

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
        } else if (password.length < 6) { // Ejemplo: mínimo 6 caracteres
            passwordError = "La contraseña debe tener al menos 6 caracteres"
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
            text = "Crea tu cuenta"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 3. Campos de Texto Actualizados ---

        // Campo de texto para el nombre
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                fullNameError = null // Limpia el error en cuanto el usuario escribe
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = fullNameError != null, // <-- Indica si hay error
            supportingText = { // <-- Muestra el mensaje de error
                if (fullNameError != null) {
                    Text(text = fullNameError!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null // Limpia el error
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
                passwordError = null // Limpia el error
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

        // --- 4. Botón de registro Actualizado ---
        Button(
            onClick = {
                // Solo ejecuta la lógica de registro si el formulario es válido
                if (validateForm()) {
                    // Formulario VÁLIDO
                    println("Datos: Nombre: $fullName, Email: $email")
                    // AQUI ira la lógica para registrar al usuario mas adelante
                } else {
                    // Formulario INVÁLIDO
                    println("El formulario contiene errores")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}