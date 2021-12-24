package com.example.mtx.ui.order.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.datasource.RetrofitServices
import com.example.mtx.dto.CustomerProductOrder
import com.example.mtx.dto.ModulesResponse
import com.example.mtx.dto.RealOrder
import com.example.mtx.dto.SkuOrdered

class OrderRepoImpl(
    private val retrofitClient: RetrofitServices,
    private val appdoa: AppDao
) : OrderRepo {

    override suspend fun customerOrder(employeeid: Int): CustomerProductOrder {
        return retrofitClient.customerOrder(employeeid)
    }

    override suspend fun skuTotalOrdered(orderid: Int): SkuOrdered {
        return retrofitClient.skuTotalOrder(orderid)
    }

    override suspend fun orderProduct(employeeid: Int, orderid: Int): RealOrder {
        return retrofitClient.orderProduct(employeeid, orderid)
    }
    
}

