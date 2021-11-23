package com.example.mtx.ui.salesrecord

import android.content.Intent
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivitySalesRecordBinding
import com.example.mtx.dto.IsParcelable
import com.example.mtx.ui.module.ModulesActivity
import com.example.mtx.ui.sales.SalesActivity
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

    private var isForceToRedirect = 0

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
        binding.toolbar.subtitle = isIntentData.data!!.outletname
    }

    private fun initAdapter() {

        binding.button.setOnClickListener {
            val intent = Intent(applicationContext, SalesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.buttons.setOnClickListener {
            binding.component.isVisible = true
            binding.awaits.isVisible = false
        }

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
                                    price_in_row->price_in_row.price!!.toDouble()*price_in_row.orders!!.toDouble()
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
        lifecycleScope.launchWhenResumed {
            binding.layerforcontent.btnComplete.setOnClickListener {
                when{
                    isIntentData.data!!.cust_token==binding.layerforcontent.tokenForm.text.toString().trim()->{
                        binding.component.isVisible = false
                        binding.awaits.isVisible = true
                        viewModel.postSalesToServer(isIntentData)
                    }
                    isIntentData.data!!.defaulttoken==binding.layerforcontent.tokenForm.text.toString().trim()->{
                        binding.component.isVisible = false
                        binding.awaits.isVisible = true
                        viewModel.postSalesToServer(isIntentData)
                    }
                    else->{
                        ToastDialog(applicationContext, "Invalid Customer Verification code").toast
                    }
                }
            }
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
                            binding.button.isVisible = false
                            binding.buttons.isVisible = true
                            binding.titles.text = "FaIL"
                            binding.subtitle.text = it.throwable!!.message.toString()
                            binding.progressBa.isVisible = false
                            binding.progressB.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.subTitles.text = "Click on the try button to retry again. Check your Mobile data."
                        }

                        is NetworkResult.Loading -> {
                        }

                        is NetworkResult.Success -> {

                            if(it.data!!.status == 200) {
                                binding.button.isVisible = true
                                binding.buttons.isVisible = false
                                binding.titles.text = "Successful"
                                binding.subtitle.text = "Synchronisation completed."
                                binding.progressBa.isVisible = true
                                binding.progressB.isVisible = false
                                binding.progressBar.isVisible = false
                                binding.subTitles.text = "Click on the completed button to Visit new customer"
                                isForceToRedirect =  1
                               return@collect
                            }

                            binding.button.isVisible = false
                            binding.buttons.isVisible = true
                            binding.titles.text = "FaIL"
                            binding.subtitle.text = "Synchronisation Failed."
                            binding.progressBa.isVisible = false
                            binding.progressB.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.subTitles.text = "Click on the try button to retry again. Check your Mobile data."
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.component.isVisible = true
        binding.awaits.isVisible = false
    }


}