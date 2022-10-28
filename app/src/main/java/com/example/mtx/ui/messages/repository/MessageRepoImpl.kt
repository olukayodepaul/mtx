package com.example.mtx.ui.messages.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.DataAccuracy
import com.example.mtx.dto.EntityAccuracy

class MessageRepoImpl (
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) : MessageRepo {

    override suspend fun dataAccuracy(customercode: String): DataAccuracy {
        return retrofitClient.dataAccuracy(customercode)
    }

    override suspend fun getDataAccuracy(): List<EntityAccuracy> {
        return appdoa.getDataAccuracy()
    }

    override suspend fun updateDataAccuracyStatus(status: Int, id: String) {
        return appdoa.updateDataAccuracyStatus(status, id)
    }

    override suspend fun getCountDataAccuracyStatus(): Int {
        return appdoa.getCountDataAccuracyStatus()
    }

}