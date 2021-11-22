package com.example.mtx.ui.salesrecord

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivitySalesRecordBinding
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.ToastDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SalesRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesRecordBinding

    private  val viewModel: SalesRecordViewModel by viewModels()

    private lateinit var adapter: SalesRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        viewModel.fetchSalesRecordEntries()
        salesRecordResponse()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.layerforcontent.recycler.layoutManager = layoutManager
        binding.layerforcontent.recycler.setHasFixedSize(true)
    }

    private fun salesRecordResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.salesRecordResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {}

                        is NetworkResult.Error -> {
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {

                            val sumInventoryRow = it.data!!.sumByDouble {
                                    price_in_row->price_in_row.inventory!!.toDouble()
                            }

                            val sumPricingRow = it.data!!.sumByDouble {
                                    price_in_row->price_in_row.pricing!!.toDouble()
                            }

                            val sumOrderRow = it.data!!.sumByDouble {
                                    price_in_row->price_in_row.orders!!.toDouble()
                            }

                            val sumAmountRow = it.data!!.sumByDouble {
                                    price_in_row->price_in_row.price!!.toDouble()
                            }

                            binding.layerforcontent.mtInventorys.text = NumberFormat.getInstance().format(sumInventoryRow)
                            binding.layerforcontent.mtPricings.text = NumberFormat.getInstance().format(sumPricingRow)
                            binding.layerforcontent.mtOrders.text = NumberFormat.getInstance().format(sumOrderRow)
                            binding.layerforcontent.mtAmounts.text = NumberFormat.getInstance().format(sumAmountRow)

                            adapter = SalesRecordAdapter(it.data!!)
                            adapter.notifyDataSetChanged()
                            binding.layerforcontent.recycler.setItemViewCacheSize(it.data!!.size)
                            binding.layerforcontent.recycler.adapter = adapter

                        }
                    }
                }
            }
        }
    }

}