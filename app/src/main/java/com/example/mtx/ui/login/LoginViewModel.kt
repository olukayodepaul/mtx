package com.example.mtx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.LoginResponseWithSpecifier
import com.example.mtx.ui.login.repository.LoginRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val repo: LoginRepo): ViewModel() {

    private val _loginResponseState = MutableStateFlow<NetworkResult<LoginResponseWithSpecifier>>(NetworkResult.Empty)
    val loginResponseState get() = _loginResponseState

    fun fetchAllSalesEntries(username: String, password: String, specifier: Boolean) = viewModelScope.launch {
        _loginResponseState.value = NetworkResult.Loading
        try {

            val isResponseWithSpecifier = LoginResponseWithSpecifier()

            if(specifier) {

                isResponseWithSpecifier.res = null
                isResponseWithSpecifier.specifier = specifier
                _loginResponseState.value = NetworkResult.Success(isResponseWithSpecifier)

            }else {

                val data = repo.isUserLogin(username, password)
                repo.deleteFromSpinnerLocalRep()
                repo.deleteBasketFromLocalRep()
                repo.deleteFromCustomersLocalRep()
                isResponseWithSpecifier.res = data
                isResponseWithSpecifier.specifier = specifier
                _loginResponseState.value = NetworkResult.Success(isResponseWithSpecifier)

            }
        } catch (e: Throwable) {
            _loginResponseState.value = NetworkResult.Error(e)
        }
    }
}