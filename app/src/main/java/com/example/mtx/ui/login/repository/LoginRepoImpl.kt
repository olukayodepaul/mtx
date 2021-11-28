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

    override suspend fun deleteBasketFromLocalRep() {
        return appdoa.deleteBasketFromLocalRep()
    }

    override suspend fun deleteFromCustomersLocalRep() {
        return appdoa.deleteFromCustomersLocalRep()
    }

    override suspend fun deleteFromSpinnerLocalRep() {
        return appdoa.deleteFromSpinnerLocalRep()
    }
}