package com.example.mtx.ui.customers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.GeneralResponse
import com.example.mtx.dto.SpinnerInterface
import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.dto.toSpinners
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
        outletLanguageId: Int, outletClassId: Int, outletTypeId: Int, outletName: String,
        contactPerson: String, mobileNumber: String,
        contactAddress: String, latitude: String, longitude: String, employee_id: Int, division: String
    ) = viewModelScope.launch {

        _isCustomerResponseState.value = NetworkResult.Loading

        try {
            val isCustomerRepoResult = repo.createCustomers(outletLanguageId, outletClassId, outletTypeId, outletName, contactPerson, mobileNumber,
                contactAddress, latitude, longitude, employee_id, division)

            _isCustomerResponseState.value = NetworkResult.Success(isCustomerRepoResult)

        }catch (e: Throwable) {

            _isCustomerResponseState.value = NetworkResult.Error(e)

        }
    }


}