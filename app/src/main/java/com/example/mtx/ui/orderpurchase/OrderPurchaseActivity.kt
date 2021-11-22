package com.example.mtx.ui.orderpurchase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityOrderPurchaseBinding

class OrderPurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}