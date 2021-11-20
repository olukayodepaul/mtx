package com.example.mtx.ui.module.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.dto.ModulesResponse

class ModulesRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) : ModulesRepo {

    override suspend fun userModules(employee_id: Int): ModulesResponse {
        return retrofitClient.userModules(employee_id)
    }
}

