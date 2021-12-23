package com.example.mtx.ui.order.repository

import com.example.mtx.dto.CustomerProductOrder
import com.example.mtx.dto.RealOrder
import com.example.mtx.dto.SkuOrdered

interface OrderRepo {
    suspend fun customerOrder(employeeid: Int): CustomerProductOrder
    suspend fun  skuTotalOrdered(orderid: Int): SkuOrdered
    suspend fun orderProduct(employeeid: Int, orderid: Int): RealOrder
}