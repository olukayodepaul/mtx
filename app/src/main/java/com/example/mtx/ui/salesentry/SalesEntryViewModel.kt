package com.example.mtx.ui.salesentry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.ModulesResponse
import com.example.mtx.dto.SalesEntryMapperInterface
import com.example.mtx.dto.toBasketLimit
import com.example.mtx.ui.salesentry.repository.SalesEntryRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SalesEntryViewModel @ViewModelInject constructor(private val repo: SalesEntryRepo) :
    ViewModel() {

    private val _basketResponseState = MutableStateFlow<NetworkResult<SalesEntryMapperInterface>>(
        NetworkResult.Empty
    )
    val basketResponseState get() = _basketResponseState

    fun isUserDailyBaskets(employee_id: Int, sysdate: String, curDate: String) =
        viewModelScope.launch {

            _basketResponseState.value = NetworkResult.Loading

            try {

                val dailyBasket = repo.fetchBasketFromLocalRep()

                val mapper = SalesEntryMapperInterface()

                if (sysdate == curDate && dailyBasket.isNotEmpty()) {

                    mapper.status = 200
                    mapper.message = ""
                    mapper.data = dailyBasket
                    _basketResponseState.value = NetworkResult.Success(mapper)

                } else {

                    val remoteData = repo.fetchBasketFromRemoteRep(employee_id)

                    val limitToSalesEntry = remoteData.basketlimit!!.filter { filters ->
                        filters.seperator.equals("1")
                    }

                    if (remoteData.status == 200 && limitToSalesEntry.isNotEmpty()) {
                        mapper.status = remoteData.status!!
                        mapper.message = remoteData.msg!!
                        mapper.data = remoteData.basketlimit!!.map { it.toBasketLimit() }
                        repo.setBasket(remoteData.basketlimit!!.map { it.toBasketLimit() }) //set the basket
                    } else {
                        mapper.message = "Basket Not Assign"
                        mapper.status = 400
                        mapper.data = emptyList()
                    }
                    _basketResponseState.value = NetworkResult.Success(mapper)
                }

            } catch (e: Throwable) {
                _basketResponseState.value = NetworkResult.Error(e)
            }
        }

    fun updateDailySales(
        inventory: Double,
        pricing: Int,
        order: Double,
        entry_time: String,
        controlpricing: Int,
        controlinventory: Int,
        controlorder: Int,
        auto: Int
    ) = viewModelScope.launch {
        repo.updateDailySales(inventory, pricing, order, entry_time, controlpricing, controlinventory, controlorder, auto)
    }


    private val _validateSalesEntryResponseState = MutableStateFlow<NetworkResult<Int>>(NetworkResult.Empty)
    val validateSalesEntryResponseState get() = _validateSalesEntryResponseState

    fun validateSalesEntries() = viewModelScope.launch {
        _validateSalesEntryResponseState.value = NetworkResult.Loading
        try {
            val data = repo.validateSalesEntry()
            _validateSalesEntryResponseState.value = NetworkResult.Success(data)
        } catch (e: Throwable) {
            _validateSalesEntryResponseState.value = NetworkResult.Error(e)
        }
    }

}