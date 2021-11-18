package com.example.mtx.ui.module.repository

import com.example.mtx.dto.ModulesResponse

interface ModulesRepo {
    suspend fun userModules(employee_id: Int): ModulesResponse
}