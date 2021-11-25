package com.example.mtx.ui.customers.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.dto.UserSpinnerResponse

class AddCustomerRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
): AddCustomerRep {

    override suspend fun fetchSpinners(): UserSpinnerResponse {
        return retrofitClient.fetchSpinners()
    }

    override suspend fun fetchSpinnerFromLocalDb(): List<UserSpinnerEntity> {
        return appdoa.fetchSpinnerFromLocalDb()
    }

    override suspend fun addCustomer(cust: List<UserSpinnerEntity>) {
        return appdoa.addCustomer(cust)
    }
}