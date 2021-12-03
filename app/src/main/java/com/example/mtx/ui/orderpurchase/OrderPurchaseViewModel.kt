package com.example.mtx.ui.orderpurchase

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.mtx.ui.orderpurchase.repository.OrderPurchaseRepo

class OrderPurchaseViewModel  @ViewModelInject constructor(private val repo : OrderPurchaseRepo): ViewModel() {

}