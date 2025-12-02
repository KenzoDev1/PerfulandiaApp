package com.example.perfulandia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.perfulandia.ui.MainScaffold
import com.example.perfulandia.ui.auth.LoginScreen
import com.example.perfulandia.ui.auth.RegistrationScreen
import com.example.perfulandia.ui.theme.PerfulandiaTheme

// Definimos los posibles estados de la pantalla raíz
enum class Screen {
    LOGIN,
    REGISTER,
    HOME
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfulandiaTheme {
                // Estado para controlar qué pantalla mostramos
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

                when (currentScreen) {
                    Screen.LOGIN -> {
                        LoginScreen(
                            onNavigateToRegister = { currentScreen = Screen.REGISTER },
                            onLoginSuccess = { currentScreen = Screen.HOME } // Al loguear, vamos al Home
                        )
                    }
                    Screen.REGISTER -> {
                        RegistrationScreen(
                            onNavigateToLogin = { currentScreen = Screen.LOGIN }
                        )
                    }
                    Screen.HOME -> {
                        // Aquí llamamos al Scaffold con el botón de salir conectado
                        MainScaffold(
                            onSignOut = {
                                // Lógica de salir: Volvemos al Login
                                currentScreen = Screen.LOGIN
                            }
                        )
                    }
                }
            }
        }
    }
}