package com.example.mtx.datasource


import com.example.mtx.dto.LoginResponse
import com.example.mtx.dto.ModulesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


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

}