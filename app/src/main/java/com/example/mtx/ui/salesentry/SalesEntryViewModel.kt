package com.example.mtx.ui.salesentry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.mtx.ui.sales.repository.SalesRepo

class SalesEntryViewModel @ViewModelInject constructor(private val repo : SalesRepo): ViewModel() {

}