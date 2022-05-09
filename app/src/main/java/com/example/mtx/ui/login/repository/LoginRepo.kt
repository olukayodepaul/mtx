package com.example.mtx.ui.login.repository


import com.example.mtx.dto.LoginResponse


interface LoginRepo {
    suspend fun isUserLogin(username: String, password: String) : LoginResponse
    suspend fun deleteBasketFromLocalRep()
    suspend fun deleteFromCustomersLocalRep()
    suspend fun deleteFromSpinnerLocalRep()
    suspend fun deleteFromMobileAgent()
}