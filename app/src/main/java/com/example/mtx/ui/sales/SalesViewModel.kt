package com.example.mtx.ui.sales

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.mtx.ui.sales.repository.SalesRepo

class SalesViewModel @ViewModelInject constructor(private val repo: SalesRepo): ViewModel() {
}