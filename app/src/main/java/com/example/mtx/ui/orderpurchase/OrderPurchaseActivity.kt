package com.example.mtx.ui.orderpurchase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mtx.databinding.ActivityOrderPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderPurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderPurchaseBinding

    private  val viewModel: OrderPurchaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}