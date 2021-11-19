package com.example.mtx.ui.sales.repository

import com.example.mtx.dto.CustomersResponse

interface SalesRepo {
    suspend fun getCustomer(employee_id: Int) : CustomersResponse
}