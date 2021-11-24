package com.example.mtx.ui.attendant.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.BasketLimitResponse

class AttendantRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) :
    AttendantRepo {

    override suspend fun fetchBasketFromRemoteRep(employee_id: Int): BasketLimitResponse {
        return retrofitClient.fetchBasketFromRemoteRep(employee_id)
    }

    override suspend fun setBasket(cust: List<BasketLimitList>) {
        return appdoa.setBasket(cust)
    }

    override suspend fun fetchBasketFromLocalRep(): List<BasketLimitList> {
        return appdoa.fetchBasketFromLocalRep()
    }

    override suspend fun deleteBasketFromLocalRep() {
        return appdoa.deleteBasketFromLocalRep()
    }

    override suspend fun setBasketToInitState() {
        return appdoa.setBasketToInitState()
    }

}