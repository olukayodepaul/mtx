package com.example.mtx.ui.sales

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.CustomersResponse
import com.example.mtx.ui.sales.repository.SalesRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SalesViewModel @ViewModelInject constructor(private val repo: SalesRepo): ViewModel() {

    private val _salesResponseState = MutableStateFlow<NetworkResult<CustomersResponse>>(NetworkResult.Empty)
    val salesResponseState get() = _salesResponseState

    fun fetchAllSalesEntries(employee_id: Int) = viewModelScope.launch {
        _salesResponseState.value = NetworkResult.Loading
        try {
            val data = repo.getCustomer(employee_id)
            _salesResponseState.value = NetworkResult.Success(data)
        } catch (e: Throwable) {
            _salesResponseState.value = NetworkResult.Error(e)
        }
    }
}