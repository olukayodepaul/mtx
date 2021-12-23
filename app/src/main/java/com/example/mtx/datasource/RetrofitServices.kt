package com.example.mtx.datasource

import com.example.mtx.dto.*
import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {

    @POST("/api/validate/defaulttoken")
    suspend fun sendTokenToday(
        @Query("urno") urno: Int,
        @Query("employee_id") employee_id: Int,
        @Query("curlocation") curlocation: String,
        @Query("region") region: Int
    ): GeneralResponse

    @GET("/api/customer/sendtokens")
    suspend fun sendTokenToday(
        @Query("unro") unro: Int
    ): sendTokenToIndividualCustomer

    @GET("/api/customer/customerorder")
    suspend fun customerOrder(
        @Query("employeeid") employeeid: Int
    ): CustomerProductOrder

    @GET("/api/customer/skuordered")
    suspend fun skuTotalOrder(
        @Query("orderid") orderid: Int
    ): SkuOrdered

    @GET("/api/customer/orderproducts")
    suspend fun orderProduct(
        @Query("employeeid") employeeid: Int,
        @Query("orderid") orderid: Int
    ): RealOrder


}