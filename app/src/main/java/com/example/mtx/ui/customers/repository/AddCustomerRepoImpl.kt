package com.example.mtx.ui.customers.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.datasource.RetrofitServices
import com.example.mtx.dto.GeneralResponse
import com.example.mtx.dto.OutletUpdateResponse
import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.dto.UserSpinnerResponse

class AddCustomerRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao,
    private val retrofitServices: RetrofitServices

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

    override suspend fun createCustomers(
        tmid:Int, rep:Int, latitude:Double, longitude:Double, outletname:String, contactname:String, outletaddress:String, contactphone:String,
        outletclassid:Int, outletlanguage:Int, outlettypeid:Int
    ) : GeneralResponse {
        return retrofitServices.mapOutlet(tmid, 0, latitude, longitude, outletname, contactname, outletaddress, contactphone,
            outletclassid, outletlanguage, outlettypeid)
    }

    override suspend fun updateOutlet(
        tmid: Int, urno: Int, latitude: Double, longitude: Double, outletname: String, contactname: String,
        outletaddress: String, contactphone: String, outletclassid: Int, outletlanguage: Int,
        outlettypeid: Int
    ): OutletUpdateResponse {
        return retrofitServices.updateOutlet(tmid,urno,latitude,longitude,outletname,contactname,outletaddress,contactphone,outletclassid,outletlanguage,outlettypeid)
    }

}