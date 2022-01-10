package com.example.mtx.ui.orderpurchase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivityOrderPurchaseBinding
import com.example.mtx.dto.Orders
import com.example.mtx.ui.module.ModuleAdapter
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.ToastDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


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
        refreshAdapter()
        customerOrderResponse()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.parentRecyclerView.layoutManager = layoutManager
        adapter = OrderAdapter()
        binding.parentRecyclerView.adapter = adapter
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.isSalesEntries(2472, 467937)
        }
    }

    private fun customerOrderResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.orderEntryResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            binding.loaders.isVisible = false
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {
                            binding.loaders.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            if(it.data!!.status==200) {
                                binding.loaders.isVisible = false
                                adapter = OrderAdapter()
                                adapter.notifyDataSetChanged()
                                binding.parentRecyclerView.setItemViewCacheSize(it.data.orderlist!!.size)
                                binding.parentRecyclerView.adapter = adapter
                                itemList(it.data.orderlist!!)
                            }else{
                                binding.loaders.isVisible = false
                                ToastDialog(applicationContext, it.data.msg!!)
                            }

                        }
                    }
                }
            }
        }
    }

    private fun itemList(list: List<Orders>) {
        adapter.addData(list)
        adapter.notifyDataSetChanged()
    }

}