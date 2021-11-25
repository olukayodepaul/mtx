package com.example.mtx.ui.attendant.repository

import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.BasketLimitResponse
import com.example.mtx.dto.GeneralResponse

interface AttendantRepo {
    suspend fun fetchBasketFromRemoteRep(employee_id: Int): BasketLimitResponse
    suspend fun setBasket(cust: List<BasketLimitList>)
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
    suspend fun deleteBasketFromLocalRep()
    suspend fun setBasketToInitState()
    suspend fun task(employee_id: Int, task_id: Int, latitude: String, longitude: String, taskname: String): GeneralResponse

}