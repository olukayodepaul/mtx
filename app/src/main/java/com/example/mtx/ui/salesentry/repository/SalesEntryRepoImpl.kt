package com.example.mtx.ui.salesentry.repository

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService

class SalesEntryRepoImpl(
    private val retrofitClient: RetrofitService,
    private val appdoa: AppDao
) : SalesEntryRepo {
}