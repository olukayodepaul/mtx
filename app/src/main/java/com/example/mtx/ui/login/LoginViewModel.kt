package com.example.mtx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.LoginResponse
import com.example.mtx.ui.login.repository.LoginRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val repo: LoginRepo): ViewModel() {

    private val _loginResponseState = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Empty)
    val loginResponseState get() = _loginResponseState

    fun fetchAllSalesEntries(username: String, password: String) = viewModelScope.launch {
        _loginResponseState.value = NetworkResult.Loading
        try {

            val data = repo.isUserLogin(username, password)
            repo.deleteFromSpinnerLocalRep()
            repo.deleteBasketFromLocalRep()
            repo.deleteFromCustomersLocalRep()
            repo.deleteFromMobileAgent()

            _loginResponseState.value = NetworkResult.Success(data)

        } catch (e: Throwable) {
            _loginResponseState.value = NetworkResult.Error(e)
        }
    }
}