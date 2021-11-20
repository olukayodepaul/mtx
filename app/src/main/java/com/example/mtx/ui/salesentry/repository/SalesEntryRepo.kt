package com.example.mtx.ui.salesentry.repository

import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.BasketLimitResponse

interface SalesEntryRepo {

    suspend fun fetchBasketFromRemoteRep(employee_id: Int): BasketLimitResponse
    suspend fun setBasket(cust: List<BasketLimitList>)
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
    suspend fun deleteBasketFromLocalRep()
}