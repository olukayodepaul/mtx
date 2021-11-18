package com.example.mtx.ui.login.repository

import com.example.mtx.dto.LoginResponse


interface LoginRepo {
    suspend fun isUserLogin(username: String, password: String) : LoginResponse
}