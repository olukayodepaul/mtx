package com.example.mtx.ui.salesrecord

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivitySalesRecordBinding
import com.example.mtx.dto.IsParcelable
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.ToastDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SalesRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesRecordBinding

    private  val viewModel: SalesRecordViewModel by viewModels()

    private lateinit var adapter: SalesRecordAdapter

    private lateinit var isIntentData: IsParcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isIntentData = intent.extras!!.getParcelable("isParcelable")!!
        initAdapter()
        viewModel.fetchSalesRecordEntries()
        salesRecordResponse()
        postSalesToServer()
        postSalesToServerResponse()
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
                                    price_in_row->price_in_row.price!!.toDouble()*price_in_row.qty!!.toDouble()
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

    private fun postSalesToServer(){
        binding.layerforcontent.btnComplete.setOnClickListener {
            binding.component.isVisible = false
            binding.awaits.isVisible = true
            viewModel.postSalesToServer(isIntentData, binding.layerforcontent.tokenForm.text.toString())
        }
    }

    private fun postSalesToServerResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.postSalesResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            binding.component.isVisible = true
                            binding.awaits.isVisible = false
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {

                        }

                        is NetworkResult.Success -> {

                        }

                    }
                }
            }
        }
    }


}