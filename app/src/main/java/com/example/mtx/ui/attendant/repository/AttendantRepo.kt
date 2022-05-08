package com.example.mtx.ui.attendant.repository

import com.example.mtx.dto.*

interface AttendantRepo {
    suspend fun fetchBasketFromRemoteRep(employee_id: Int): BasketLimitResponse
    suspend fun setBasket(cust: List<BasketLimitList>)
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>
    suspend fun deleteBasketFromLocalRep()
    suspend fun setBasketToInitState()
    suspend fun task(employee_id: Int, task_id: Int, latitude: String, longitude: String, taskname: String): GeneralResponse
    suspend fun setAttendantTime(timeago:String, sort:Int )
    suspend fun resetError(employee_id:Int, product_code:String, qty:Double ): OrderError
    suspend fun resetPostEntry(auto:Int, total:Double )
    suspend fun allDailyAssignedAgents(route_id:String) : List<IsMoneyAgent>
}