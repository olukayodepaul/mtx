package com.example.mtx.ui.sales.repository

import com.example.mtx.dto.CustomersList
import com.example.mtx.dto.CustomersResponse

interface SalesRepo {
    suspend fun getCustomer(employee_id: Int) : CustomersResponse
    suspend fun getCustomers() : List<CustomersList>
    suspend fun addCustomers(cust: List<CustomersList>)
}