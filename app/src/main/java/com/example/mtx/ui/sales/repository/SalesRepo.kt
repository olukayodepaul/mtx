package com.example.mtx.ui.sales.repository

import com.example.mtx.dto.*
import retrofit2.http.Query

interface SalesRepo {
    suspend fun getCustomer(employee_id: Int) : CustomersResponse
    suspend fun getCustomers() : List<CustomersList>
    suspend fun addCustomers(cust: List<CustomersList>)
    suspend fun postSales(order: OrderPosted): PostSalesResponse
    suspend fun sendTokenToday(unro: Int): sendTokenToIndividualCustomer
}