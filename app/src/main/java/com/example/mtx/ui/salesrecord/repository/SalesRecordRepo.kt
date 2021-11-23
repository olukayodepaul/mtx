package com.example.mtx.ui.salesrecord.repository

import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.OrderPosted
import com.example.mtx.dto.PostSalesResponse


interface SalesRecordRepo {
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
    suspend fun postSales(order: OrderPosted): PostSalesResponse
    suspend fun salesPosted(): List<BasketLimitList>
    suspend fun resetOrders(order_sold: Double, auto: Int)
}

