package com.example.mtx.ui.salesentry.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.BasketLimitResponse

class SalesEntryRepoImpl(

    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao

) : SalesEntryRepo {
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

    override suspend fun updateDailySales(
        inventory: Double,
        pricing: Int,
        order: Double,
        entry_time: String,
        controlpricing: Int,
        controlinventory: Int,
        controlorder: Int,
        auto: Int
    ) {
        return appdoa.updateDailySales(inventory, pricing, order, entry_time, controlpricing, controlinventory, controlorder, auto)
    }

    override suspend fun validateSalesEntry(): Int {
        return appdoa.validateSalesEntry()
    }

    override suspend fun setBasketToInitState() {
        return appdoa.setBasketToInitState()
    }


}
