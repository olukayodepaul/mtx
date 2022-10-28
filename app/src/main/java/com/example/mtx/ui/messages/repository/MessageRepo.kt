package com.example.mtx.ui.messages.repository

import com.example.mtx.dto.DataAccuracy
import com.example.mtx.dto.EntityAccuracy

interface MessageRepo {
    suspend fun dataAccuracy(customercode: String) : DataAccuracy
    suspend fun getDataAccuracy() : List<EntityAccuracy>
    suspend fun updateDataAccuracyStatus(status:Int,id:String)
    suspend fun getCountDataAccuracyStatus(): Int
}