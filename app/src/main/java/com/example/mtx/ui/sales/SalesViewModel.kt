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
                println("EPOLOG 2")
                val getFromLocalReo = repo.getCustomers()

                if(getFromLocalReo.isNullOrEmpty()) {
                    println("EPOLOG 3")
                    mapper.entries = emptyList()
                    mapper.message = "Please check your phone storage"
                    mapper.status = 400
                }else {
                    println("EPOLOG 4")
                    mapper.entries = getFromLocalReo
                    mapper.message = ""
                    mapper.status = 200
                }

                _salesResponseState.value = NetworkResult.Success(mapper)

            }else {

                val pullFromRemoteRepo = repo.getCustomer(employee_id)

                println("EPOLOG 6")
                if(pullFromRemoteRepo.status==200) {
                    if(pullFromRemoteRepo.customers.isNullOrEmpty()) {
                        println("EPOLOG 7")
                        mapper.entries = emptyList()
                        mapper.message = "Customer is not assigned"
                        mapper.status = 400
                    }else{
                        println("EPOLOG 8")
                        repo.addCustomers(repo.getCustomer(employee_id).customers!!.map { it.toSalesEntry()})
                        val getFromLocalReo = repo.getCustomers()
                        if(getFromLocalReo.isNullOrEmpty()){
                            mapper.entries = emptyList()
                            mapper.message = "Please check your phone storage"
                            mapper.status = 400
                        }else{
                            println("EPOLOG 9")
                            mapper.entries = getFromLocalReo
                            mapper.message = ""
                            mapper.status = 200
                        }
                    }
                }else{
                    println("EPOLOG 10")
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
}
