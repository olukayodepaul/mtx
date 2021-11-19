package com.example.mtx.ui.salesentry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtx.databinding.ActivitySalesEntryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SalesEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesEntryBinding

    private lateinit var viewModel: SalesEntryViewModel

    private lateinit var adapter: SalesEntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}