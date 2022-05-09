package com.example.mtx.ui.attendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivityMobileMoneyAgentBinding
import com.example.mtx.ui.order.OrderSummaryAdapter
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.ToastDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MobileMoneyAgent : AppCompatActivity() {

    private lateinit var binding: ActivityMobileMoneyAgentBinding

    private val viewModel: AttendantViewModel by viewModels()

    private lateinit var adapter: MobileMoneyAgentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileMoneyAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        viewModel.isMobileMoneyAgent("")
        agentStateFlow()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.setHasFixedSize(true)
    }

    private fun agentStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.isMoneyAgentsResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {}

                        is NetworkResult.Error -> {}

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {

                            val isCacheData = it.data

                            if(isCacheData!!.isNotEmpty()){

                                adapter = MobileMoneyAgentAdapter(isCacheData)
                                adapter.notifyDataSetChanged()
                                binding.recycler.setItemViewCacheSize(isCacheData.size)
                                binding.recycler.adapter = adapter

                            }else{
                                //call the end point to fill this data.........

                            }
                        }
                    }
                }
            }
        }
    }

    //calling the end point


}