package com.example.mtx.ui.sales.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.CustomersList
import com.example.mtx.dto.CustomersResponse

class SalesRepoImpl (private val retrofitClient: RetrofitService, private val appdoa: AppDao): SalesRepo {

    override suspend fun getCustomer(employee_id: Int): CustomersResponse {
        return retrofitClient.getCustomers(employee_id)
    }

    override suspend fun getCustomers(): List<CustomersList> {
        return appdoa.getCustomers()
    }

    override suspend fun addCustomers(cust: List<CustomersList>) {
        return appdoa.addCustomers(cust)
    }

}