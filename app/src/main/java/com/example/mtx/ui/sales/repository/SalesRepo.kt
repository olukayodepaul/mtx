package com.example.mtx.ui.sales.repository

import com.example.mtx.dto.CustomersList
import com.example.mtx.dto.CustomersResponse
import com.example.mtx.dto.OrderPosted
import com.example.mtx.dto.PostSalesResponse

interface SalesRepo {
    suspend fun getCustomer(employee_id: Int) : CustomersResponse
    suspend fun getCustomers() : List<CustomersList>
    suspend fun addCustomers(cust: List<CustomersList>)
    suspend fun postSales(order: OrderPosted): PostSalesResponse
}