package com.example.perfulandia.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.model.LoginRequest
import com.example.perfulandia.model.User
import com.example.perfulandia.model.RegisterRequest
import com.example.perfulandia.model.UserMetadata
import com.example.perfulandia.retrofit.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User, val token: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    // NUEVO ESTADO: Registro exitoso (pero sin login automático)
    object RegistrationSuccess : LoginUiState()
}

class LoginViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    // LOGIN (Se mantiene igual: devuelve Success con usuario y token)
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val request = LoginRequest(email = email, password = pass)
                val response = apiService.signIn(request)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    _uiState.value = LoginUiState.Success(authResponse.user, authResponse.accessToken)
                } else {
                    val errorMsg = when(response.code()) {
                        400 -> "Credenciales inválidas"
                        401 -> "Usuario no verificado o contraseña incorrecta"
                        else -> "Error en el servidor: ${response.code()}"
                    }
                    _uiState.value = LoginUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error de conexión")
            }
        }
    }

    // REGISTRO (MODIFICADO: Ahora devuelve RegistrationSuccess)
    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val metadata = UserMetadata(name = name, rol = "Cliente")
                val request = RegisterRequest(email = email, password = pass, data = metadata)
                val response = apiService.signUp(request)

                if (response.isSuccessful && response.body() != null) {
                    // CAMBIO AQUÍ: En lugar de iniciar sesión (Success),
                    // solo avisamos que el registro funcionó.
                    _uiState.value = LoginUiState.RegistrationSuccess
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                    _uiState.value = LoginUiState.Error("Error registro: $errorMsg")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error de conexión")
            }
        }
    }

    fun clearError() { _uiState.value = LoginUiState.Idle }
}