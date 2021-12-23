package com.example.mtx.ui.sales

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.*
import com.example.mtx.ui.sales.repository.SalesRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SalesViewModel @ViewModelInject constructor(private val repo: SalesRepo): ViewModel() {

    private val _salesResponseState = MutableStateFlow<NetworkResult<IsAllCustomers>>(NetworkResult.Empty)
    val salesResponseState get() = _salesResponseState

    fun fetchAllSalesEntries(employee_id: Int, cacheDate: String, currentDate: String) = viewModelScope.launch {
        _salesResponseState.value = NetworkResult.Loading
        try {
            val mapper = IsAllCustomers()

            if(cacheDate==currentDate){
                val getFromLocalReo = repo.getCustomers()

                if(getFromLocalReo.isNullOrEmpty()) {
                    mapper.entries = emptyList()
                    mapper.message = "Please check your phone storage"
                    mapper.status = 400
                }else {
                    mapper.entries = getFromLocalReo
                    mapper.message = ""
                    mapper.status = 200
                }

                _salesResponseState.value = NetworkResult.Success(mapper)

            }else {

                val pullFromRemoteRepo = repo.getCustomer(employee_id)
                if(pullFromRemoteRepo.status==200) {
                    if(pullFromRemoteRepo.customers.isNullOrEmpty()) {
                        mapper.entries = emptyList()
                        mapper.message = "Customer is not assigned"
                        mapper.status = 400
                    }else{
                        repo.addCustomers(repo.getCustomer(employee_id).customers!!.map { it.toSalesEntry()})
                        val getFromLocalReo = repo.getCustomers()
                        if(getFromLocalReo.isNullOrEmpty()){
                            mapper.entries = emptyList()
                            mapper.message = "Please check your phone storage"
                            mapper.status = 400
                        }else{
                            mapper.entries = getFromLocalReo
                            mapper.message = ""
                            mapper.status = 200
                        }
                    }
                }else{
                    mapper.entries = emptyList()
                    mapper.message = pullFromRemoteRepo.msg!!
                    mapper.status = pullFromRemoteRepo.status!!
                }

                _salesResponseState.value = NetworkResult.Success(mapper)
            }
        } catch (e: Throwable) {
            _salesResponseState.value = NetworkResult.Error(e)
        }
    }

    private val _closeOutletResponseState = MutableStateFlow<NetworkResult<PostSalesResponse>>(NetworkResult.Empty)
    val closeOutletResponseState get() = _closeOutletResponseState

    fun fetchAllSalesEntries(salesRecord: IsParcelable) = viewModelScope.launch {
        _closeOutletResponseState.value = NetworkResult.Loading
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
            isResponseModel.remark = salesRecord.remark
            isResponseModel.order = emptyList()


            val data = repo.postSales(isResponseModel)
            _closeOutletResponseState.value = NetworkResult.Success(data)

        } catch (e: Throwable) {
            _closeOutletResponseState.value = NetworkResult.Error(e)
        }
    }

    fun sentToken(urno:Int) = viewModelScope.launch {
        try { val data = repo.sendTokenToday(urno) } catch (e: Throwable) {}
    }

}
