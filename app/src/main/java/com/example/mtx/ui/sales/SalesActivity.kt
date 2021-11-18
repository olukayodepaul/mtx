
package com.example.mtx.ui.sales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mtx.databinding.ActivitySalesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesActivity : AppCompatActivity() {

    lateinit var binding: ActivitySalesBinding

    private val viewModel: SalesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}