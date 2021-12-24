package com.example.mtx.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.databinding.ActivityOrderSummaryBinding
import com.example.mtx.dto.AllCustomerProductOrder
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.DecimalFormat

@AndroidEntryPoint
class OrderSummary : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSummaryBinding

    private lateinit var passData: AllCustomerProductOrder

    private lateinit var adapter: OrderSummaryAdapter

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        passData = intent.extras!!.getParcelable("isParcelable")!!
        isCustomerInfo()
        initAdapter()
        viewModel.isSkuOrdered(passData.orderid)

        isAllSkuOrderedFor()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun isCustomerInfo() {
        binding.custName.text = passData.outletname
        binding.address.text = passData.outletaddress
        binding.contactphone.text = passData.contactphone
        binding.orderId.text = passData.orderid.toString()
    }


    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recycleOrderHistory.layoutManager = layoutManager
        binding.recycleOrderHistory.setHasFixedSize(true)
    }


    private fun isAllSkuOrderedFor() = lifecycleScope.launchWhenResumed {
        viewModel.skuOrderedResponseState.collect {
            it.let {
                when (it) {
                    is NetworkResult.Empty -> {

                    }

                    is NetworkResult.Loading -> {

                    }

                    is NetworkResult.Error -> {
                        //the error message
                    }
                    is NetworkResult.Success -> {
                        val df = DecimalFormat("#.#")
                        adapter = OrderSummaryAdapter(it.data!!.skuorder!!)
                        adapter.notifyDataSetChanged()
                        binding.recycleOrderHistory.setItemViewCacheSize(it.data.skuorder!!.size)
                        binding.recycleOrderHistory.adapter = adapter
                        binding.priceTvs.text = String.format("%,.1f", (df.format(it.data.totalamount*it.data.totalqty).toDouble())).trim()
                        binding.qtyTvs.text = it.data.totalqty.toString()
                    }

                }
            }
        }
    }


}