package com.example.mtx.ui.attendant

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.*
import com.example.mtx.ui.attendant.repository.AttendantRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AttendantViewModel @ViewModelInject constructor(private val repo: AttendantRepo) :
    ViewModel() {

    private val _basketResponseState =
        MutableStateFlow<NetworkResult<SalesEntryMapperInterface>>(NetworkResult.Empty)
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

    private val _taskResponseState =
        MutableStateFlow<NetworkResult<GeneralResponse>>(NetworkResult.Empty)
    val taskResponseState get() = _taskResponseState

    fun recordTask(
        employee_id: Int,
        task_id: Int,
        latitude: String,
        longitude: String,
        taskname: String,
        timeAgo:String,
        sortId:Int
    ) = viewModelScope.launch {
        _taskResponseState.value = NetworkResult.Loading
        try {
            val data = repo.task(employee_id, task_id, latitude, longitude, taskname)

            if(data.status==200){
                repo.setAttendantTime(timeAgo, sortId)
            }

            _taskResponseState.value = NetworkResult.Success(data)
        } catch (e: Throwable) {
            _taskResponseState.value = NetworkResult.Error(e)
        }
    }

    private val _errorCorrectionResponseState = MutableStateFlow<NetworkResult<OrderError>>(NetworkResult.Empty)
    val errorCorrectionResponseState get() = _errorCorrectionResponseState

    fun fetchError(employee_id: Int, product_code: String, auto:Int, qty:Double) = viewModelScope.launch {
        _errorCorrectionResponseState.value = NetworkResult.Loading
        try {
            val data = repo.resetError(employee_id, product_code, qty)
            repo.resetPostEntry(auto, data.sum!!)
            _errorCorrectionResponseState.value = NetworkResult.Success(data)
        } catch (e: Throwable) {
            _errorCorrectionResponseState.value = NetworkResult.Error(e)
        }
    }

    private val _isMoneyAgentsResponseState = MutableStateFlow<NetworkResult<List<IsMoneyAgent>>>(NetworkResult.Empty)
    val isMoneyAgentsResponseState get() = _isMoneyAgentsResponseState

    fun isMobileMoneyAgent(route_id:String) = viewModelScope.launch {
        _isMoneyAgentsResponseState.value = NetworkResult.Loading
        try {
            _isMoneyAgentsResponseState.value = NetworkResult.Success(repo.allDailyAssignedAgents(route_id))
        } catch (e: Throwable) {
            _isMoneyAgentsResponseState.value = NetworkResult.Error(e)
        }
    }

}