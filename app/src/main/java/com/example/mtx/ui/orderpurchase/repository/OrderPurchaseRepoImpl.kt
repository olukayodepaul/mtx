package com.example.mtx.ui.orderpurchase.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService

class OrderPurchaseRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) : OrderPurchaseRepo {

}

