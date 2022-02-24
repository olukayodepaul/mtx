package com.example.mtx.ui.customers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.*
import com.example.mtx.ui.customers.repository.AddCustomerRep
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddCustomerViewModel @ViewModelInject constructor(private val repo: AddCustomerRep) :
    ViewModel() {


    private val _spinnerResponseState =
        MutableStateFlow<NetworkResult<SpinnerInterface>>(NetworkResult.Empty)
    val spinnerResponseState get() = _spinnerResponseState

    fun fetchAllSpinners() = viewModelScope.launch {

        _spinnerResponseState.value = NetworkResult.Loading

        try {

            val checkLocalRepIfSpinnerDataIsAvail = repo.fetchSpinnerFromLocalDb()
            val list = SpinnerInterface()

            if (checkLocalRepIfSpinnerDataIsAvail.isEmpty()) {

                val fetchSpinnerFromRemoteRepo = repo.fetchSpinners()

                if (fetchSpinnerFromRemoteRepo.status == 200) {

                    val convertedCustomers =
                        fetchSpinnerFromRemoteRepo.spinners!!.map { it.toSpinners() }
                    repo.addCustomer(convertedCustomers)

                    list.status = fetchSpinnerFromRemoteRepo.status!!
                    list.message = fetchSpinnerFromRemoteRepo.msg!!
                    list.data = convertedCustomers
                    spinnerResponseState.value = NetworkResult.Success(list)
                } else {

                    val list = SpinnerInterface()
                    list.status = fetchSpinnerFromRemoteRepo.status!!
                    list.message = fetchSpinnerFromRemoteRepo.msg!!
                    list.data = emptyList()
                    spinnerResponseState.value = NetworkResult.Success(list)
                }

            } else {
                val list = SpinnerInterface()
                list.status = 200
                list.message = ""
                list.data = checkLocalRepIfSpinnerDataIsAvail
                spinnerResponseState.value = NetworkResult.Success(list)
            }


        } catch (e: Throwable) {
            _spinnerResponseState.value = NetworkResult.Error(e)
        }
    }


    private val _isCustomerResponseState = MutableStateFlow<NetworkResult<GeneralResponse>>(NetworkResult.Empty)
    val isCustomerResponseState get() = _isCustomerResponseState

    fun createCustomers(
        tmid: Int,  latitude: Double, longitude: Double, outletname: String, contactname: String,
        outletaddress: String, contactphone: String, outletclassid: Int, outletlanguage: Int,
        outlettypeid: Int
    ) = viewModelScope.launch {
        _isCustomerResponseState.value = NetworkResult.Loading
        try {
            val isCustomerRepoResult = repo.createCustomers(
                tmid, tmid, latitude, longitude, outletname, contactname, outletaddress, contactphone,
                outletclassid, outletlanguage, outlettypeid
            )
            _isCustomerResponseState.value = NetworkResult.Success(isCustomerRepoResult)
        }catch (e: Throwable) {
            _isCustomerResponseState.value = NetworkResult.Error(e)
        }
    }

    //Update on outlets
    private val _isCustomerUpdateResponseState = MutableStateFlow<NetworkResult<OutletUpdateResponse>>(NetworkResult.Empty)
    val isCustomerUpdateResponseState get() = _isCustomerUpdateResponseState

    fun updateCustomers(
        tmid: Int, urno: Int, latitude: Double, longitude: Double, outletname: String, contactname: String,
        outletaddress: String, contactphone: String, outletclassid: Int, outletlanguage: Int,
        outlettypeid: Int
    ) = viewModelScope.launch {
        _isCustomerUpdateResponseState.value = NetworkResult.Loading
        try {
            val isCustomerRepoResult = repo.updateOutlet(tmid,urno,latitude,longitude,outletname,contactname,outletaddress,contactphone,outletclassid,outletlanguage,outlettypeid)
            _isCustomerUpdateResponseState.value = NetworkResult.Success(isCustomerRepoResult)
        }catch (e: Throwable) {
            _isCustomerUpdateResponseState.value = NetworkResult.Error(e)
        }
    }


}