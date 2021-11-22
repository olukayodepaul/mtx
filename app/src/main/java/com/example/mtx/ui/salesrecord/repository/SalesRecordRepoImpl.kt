package com.example.mtx.ui.salesrecord.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.BasketLimitList

class SalesRecordRepoImpl (

    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao

) : SalesRecordRepo {
    override suspend fun fetchBasketFromLocalRep(): List<BasketLimitList> {
        return appdoa.fetchBasketFromLocalRep()
    }
}