package com.example.mtx.ui.sales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivitySalesBinding
import com.example.mtx.databinding.SalesAdapterBinding
import com.example.mtx.dto.CustomersList
import com.example.mtx.ui.order.ReOrderActivity
import com.example.mtx.util.GeoFencing
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.nex3z.notificationbadge.NotificationBadge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class SalesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesBinding

    private val viewModel: SalesViewModel by viewModels()

    private lateinit var adapter: SalesAdapter

    private lateinit var sessionManager: SessionManager

    var searchView: SearchView? = null

    var notificationBadgeView: View? = null

    var notificationBadge: NotificationBadge? = null

    var item_Notification: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        setSupportActionBar(binding.toolbar)
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
            println("EPOLOG 1 ${sessionManager.fetchEmployeeId.first()}")
            viewModel.fetchAllSalesEntries(
                738,
                sessionManager.fetchCustomerEntryDate.first(),
                GeoFencing.currentDate!!
            )
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
                            binding.tvCustomers.isVisible = false
                            binding.progressbarHolder.isVisible = false
                        }

                        is NetworkResult.Loading -> {
                            binding.customOverlayView.root.isVisible = false
                            binding.tvCustomers.isVisible = false
                        }

                        is NetworkResult.Success -> {

                            binding.progressbarHolder.isVisible = false

                            if (it.data!!.status == 200) {

                                binding.customOverlayView.root.isVisible = false
                                binding.tvCustomers.isVisible = true

                                sessionManager.storeCustomerEntryDate(GeoFencing.currentDate!!)

                                adapter = SalesAdapter(it.data.entries!!, applicationContext, ::handleAdapterEvent)
                                adapter.notifyDataSetChanged()
                                binding.tvCustomers.setItemViewCacheSize(it.data.entries!!.size)
                                binding.tvCustomers.adapter = adapter

                            } else {

                                binding.customOverlayView.tvNetwork.text = it.data.message
                                binding.customOverlayView.root.isVisible = true
                                binding.tvCustomers.isVisible = false

                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleAdapterEvent(
        item: CustomersList,
        separators: Int,
        adapterBinding: SalesAdapterBinding
    ) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_search -> {

            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visitdetails, menu)
        item_Notification = menu!!.findItem(R.id.action_notifications)
        notificationBadgeView = item_Notification!!.actionView
        notificationBadge = notificationBadgeView!!.findViewById(R.id.badge) as NotificationBadge

        notificationBadgeView!!.setOnClickListener {
            val intent = Intent(applicationContext, ReOrderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        setupBadge()
        return true
    }

    private fun setupBadge() {
        notificationBadge!!.isVisible = true
        notificationBadge!!.setText("10")
    }

}