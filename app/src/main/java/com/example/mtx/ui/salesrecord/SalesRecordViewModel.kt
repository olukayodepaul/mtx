package com.example.mtx.ui.salesrecord

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.ui.salesrecord.repository.SalesRecordRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SalesRecordViewModel @ViewModelInject constructor(private val repo: SalesRecordRepo) :ViewModel() {

    private val _salesRecordResponseState = MutableStateFlow<NetworkResult<List<BasketLimitList>>>(
        NetworkResult.Empty)
    val salesRecordResponseState get() = _salesRecordResponseState

    fun fetchSalesRecordEntries() = viewModelScope.launch {
        _salesRecordResponseState.value = NetworkResult.Loading
        try {
            val data = repo.fetchBasketFromLocalRep()
            _salesRecordResponseState.value = NetworkResult.Success(data)
        } catch (e: Throwable) {
            _salesRecordResponseState.value = NetworkResult.Error(e)
        }
    }

}