package com.example.mtx.datasource

import com.example.mtx.dto.*
import retrofit2.http.*


interface RetrofitServices {

    @Headers("Connection:close")
    @POST("/api/validate/defaulttoken")
    suspend fun sendTokenToday(
        @Query("urno") urno: Int,
        @Query("employee_id") employee_id: Int,
        @Query("curlocation") curlocation: String,
        @Query("region") region: Int
    ): GeneralResponse


}