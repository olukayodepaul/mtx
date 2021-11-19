
package com.example.mtx.ui.sales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivitySalesBinding
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class SalesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesBinding

    private val viewModel: SalesViewModel by viewModels()

    private lateinit var adapter: SalesAdapter

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        initAdapter()
        refreshAdapter()
        salesResponse()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.tvCustomers.layoutManager = layoutManager
        binding.tvCustomers.setHasFixedSize(true)
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchAllSalesEntries(sessionManager.fetchEmployeeId.first())
        }
    }

    private fun salesResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.salesResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {

                        }

                        is NetworkResult.Error -> {
                            binding.customOverlayView.tvNetwork.text = it.throwable!!.message.toString()
                            binding.customOverlayView.root.isVisible = true
                            binding.responseLoader.isVisible = false
                            binding.tvCustomers.isVisible = false
                        }

                        is NetworkResult.Loading -> {
                            binding.customOverlayView.root.isVisible = false
                            binding.responseLoader.isVisible = true
                            binding.tvCustomers.isVisible = false
                        }

                        is NetworkResult.Success -> {

                            binding.customOverlayView.root.isVisible = false
                            binding.responseLoader.isVisible = false
                            binding.tvCustomers.isVisible = true

                            adapter = SalesAdapter(it.data!!.customers!!, applicationContext)
                            adapter.notifyDataSetChanged()
                            binding.tvCustomers.setItemViewCacheSize(it.data.customers!!.size)
                            binding.tvCustomers.adapter = adapter
                        }
                    }
                }
            }
        }
    }

}