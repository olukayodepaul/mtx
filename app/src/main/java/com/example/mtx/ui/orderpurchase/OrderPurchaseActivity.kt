package com.example.mtx.ui.orderpurchase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivityOrderPurchaseBinding
import com.example.mtx.dto.Orders
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderPurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderPurchaseBinding

    private  val viewModel: OrderPurchaseViewModel by viewModels()

    private lateinit var adapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.parentRecyclerView.layoutManager = layoutManager
        adapter = OrderAdapter()
        binding.parentRecyclerView.adapter = adapter
        itemList(emptyList()) //here is the empty list
        binding.parentRecyclerView.setHasFixedSize(true)
    }

    private fun itemList(gameOfThrones: List<Orders>) {
        adapter.addData(gameOfThrones)
        adapter.notifyDataSetChanged()
    }

}