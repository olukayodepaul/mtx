package com.example.mtx.ui.salesrecord.repository

import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.GeneralResponse
import com.example.mtx.dto.OrderPosted
import com.example.mtx.dto.PostSalesResponse
import retrofit2.http.Query


interface SalesRecordRepo {
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
    suspend fun postSales(order: OrderPosted): PostSalesResponse
    suspend fun salesPosted(): List<BasketLimitList>
    suspend fun resetOrders(auto: Int)
    suspend fun setVisitTime(timeago:String, urno:Int )
    suspend fun sendTokenToday(urno: Int, employee_id: Int, curlocation: String, region: Int): GeneralResponse
}

