package com.example.mtx.datasource


import com.example.mtx.dto.*
import retrofit2.http.*


interface RetrofitService {

    @Headers("Connection:close")
    @GET("/mtx/users")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): LoginResponse

    @Headers("Connection:close")
    @GET("/mtx/usermodules")
    suspend fun userModules(
        @Query("employee_id") employee_id: Int
    ): ModulesResponse

    @Headers("Connection:close")
    @GET("/sales/customers")
    suspend fun getCustomers(
        @Query("employee_id") employee_id: Int
    ): CustomersResponse

    @Headers("Connection:close")
    @GET("/sales/basketlimit")
    suspend fun fetchBasketFromRemoteRep(
        @Query("employee_id") employee_id: Int
    ): BasketLimitResponse

    @Headers("Connection:close")
    @POST("/sales/dailysales")
    suspend fun postSales(
        @Body data: OrderPosted
    ): PostSalesResponse

    @Headers("Connection:close")
    @POST("/users/task")
    suspend fun task(
        @Query("employee_id") employee_id: Int,
        @Query("task_id") task_id: Int,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("taskname") taskname: String
    ): GeneralResponse




}