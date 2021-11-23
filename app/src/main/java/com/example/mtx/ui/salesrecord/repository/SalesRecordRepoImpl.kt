package com.example.mtx.ui.salesrecord.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.OrderPosted
import com.example.mtx.dto.PostSalesResponse


class SalesRecordRepoImpl (

    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao

) : SalesRecordRepo {

    override suspend fun fetchBasketFromLocalRep(): List<BasketLimitList> {
        return appdoa.fetchBasketFromLocalRep()
    }

    override suspend fun postSales(order: OrderPosted): PostSalesResponse {
        return retrofitClient.postSales(order)
    }

    override suspend fun salesPosted(): List<BasketLimitList> {
        return appdoa.salesPosted()
    }

    override suspend fun resetOrders(auto: Int) {
        return appdoa.resetOrders(auto)
    }

    override suspend fun setVisitTime(timeago: String, urno: Int) {
        return appdoa.setVisitTime(timeago, urno)
    }


}