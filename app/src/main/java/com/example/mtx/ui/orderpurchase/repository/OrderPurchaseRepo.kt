package com.example.mtx.ui.orderpurchase.repository

import com.example.mtx.dto.OrderParentList


interface OrderPurchaseRepo {
    suspend fun isNetworkHelper(): Boolean
    suspend fun isSalesEntry(employee_id: Int, urno: Int): OrderParentList
}