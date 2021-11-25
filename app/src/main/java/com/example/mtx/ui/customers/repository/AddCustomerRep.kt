package com.example.mtx.ui.customers.repository

import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.dto.UserSpinnerResponse

interface AddCustomerRep {
    suspend fun fetchSpinners(): UserSpinnerResponse
    suspend fun fetchSpinnerFromLocalDb() : List<UserSpinnerEntity>
    suspend fun addCustomer(cust: List<UserSpinnerEntity>)
}