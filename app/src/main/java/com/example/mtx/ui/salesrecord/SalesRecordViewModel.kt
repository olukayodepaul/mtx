package com.example.mtx.ui.salesrecord

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.*
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

    private val _postSalesResponseState = MutableStateFlow<NetworkResult<PostSalesResponse>>(NetworkResult.Empty)
    val postSalesResponseState get() = _postSalesResponseState

    fun postSalesToServer(salesRecord: IsParcelable) = viewModelScope.launch {
        _postSalesResponseState.value = NetworkResult.Loading
        try {

            val isResponseModel = OrderPosted()
            isResponseModel.uiid = salesRecord.uii
            isResponseModel.clat = salesRecord.latitude!!.toString()
            isResponseModel.clng = salesRecord.longitude!!.toString()
            isResponseModel.etime = salesRecord.entry_time
            isResponseModel.edate = salesRecord.entry_date
            isResponseModel.customerno = salesRecord.data!!.customerno
            isResponseModel.employee_id = salesRecord.data!!.employee_id
            isResponseModel.urno = salesRecord.data!!.urno
            isResponseModel.outletlatitude = salesRecord.data!!.latitude
            isResponseModel.outletlongitude = salesRecord.data!!.longitude
            isResponseModel.outletname = salesRecord.data!!.outletname
            isResponseModel.volumeclass = salesRecord.data!!.volumeclass
            isResponseModel.token = salesRecord.userToken
            isResponseModel.order = repo.salesPosted().map { it.toBasketToApi() }
            val httpResponse = repo.postSales(isResponseModel)

            if


        } catch (e: Throwable) {
            _postSalesResponseState.value = NetworkResult.Error(e)
        }
    }
}