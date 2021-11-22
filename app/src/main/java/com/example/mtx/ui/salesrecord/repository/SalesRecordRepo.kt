package com.example.mtx.ui.salesrecord.repository

import com.example.mtx.dto.BasketLimitList

interface SalesRecordRepo {
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
}