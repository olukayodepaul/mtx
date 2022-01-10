package com.example.mtx.ui.orderpurchase.repository



import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.di.NetworkHelper
import com.example.mtx.dto.OrderParentList


class OrderPurchaseRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao,
    private val networkHelper: NetworkHelper
) : OrderPurchaseRepo {

    override suspend fun isNetworkHelper(): Boolean {
        return networkHelper.isNetworkConnected()
    }

    override suspend fun isSalesEntry(employee_id: Int, urno: Int): OrderParentList {
        return retrofitClient.orderPurchase(employee_id, urno)
    }

}

