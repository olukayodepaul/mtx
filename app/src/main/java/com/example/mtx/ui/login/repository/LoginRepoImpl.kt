package com.example.mtx.ui.login.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.LoginResponse


class LoginRepoImpl (
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) : LoginRepo{
    override suspend fun isUserLogin(
        username: String,
        password: String,
    ): LoginResponse {
        return retrofitClient.login(username, password)
    }
}