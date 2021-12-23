package com.example.mtx.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivityOrderSummaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSummary : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}