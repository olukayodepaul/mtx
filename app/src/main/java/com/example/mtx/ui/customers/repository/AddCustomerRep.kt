package com.example.mtx.ui.customers.repository

import com.example.mtx.dto.GeneralResponse
import com.example.mtx.dto.OutletUpdateResponse
import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.dto.UserSpinnerResponse

interface AddCustomerRep {
    suspend fun fetchSpinners(): UserSpinnerResponse
    suspend fun fetchSpinnerFromLocalDb() : List<UserSpinnerEntity>
    suspend fun addCustomer(cust: List<UserSpinnerEntity>)
    suspend fun createCustomers(
        outletLanguageId: Int, outletClassId: Int, outletTypeId: Int, outletName: String,
        contactPerson: String, mobileNumber: String,
        contactAddress: String, latitude: String, longitude: String, employee_id: Int, division: String
    ): GeneralResponse

    suspend fun updateOutlet(tmid: Int, urno: Int, latitude: Double, longitude: Double, outletname: String, contactname: String,
                 outletaddress: String, contactphone: String, outletclassid: Int, outletlanguage: Int,
                 outlettypeid: Int): OutletUpdateResponse

}