package com.example.mtx.ui.salesentry

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivitySalesEntryBinding
import com.example.mtx.databinding.SalesEntryAdapterBinding
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.ui.order.ReOrderActivity
import com.example.mtx.util.GeoFencing
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.example.mtx.util.ToastDialog
import com.nex3z.notificationbadge.NotificationBadge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@AndroidEntryPoint
class SalesEntryActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivitySalesEntryBinding

    private val viewModel: SalesEntryViewModel by viewModels()

    private lateinit var adapter: SalesEntryAdapter

    private lateinit var sessionManager: SessionManager

    var notificationBadgeView: View? = null

    var notificationBadge: NotificationBadge? = null

    var item_Notification: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        sessionManager = SessionManager(this)
        initAdapter()
        refreshAdapter()
        basketResponse()
        binding.loader.refreshImG.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun refreshAdapter() {
        lifecycleScope.launchWhenCreated {
            viewModel.isUserDailyBaskets(sessionManager.fetchEmployeeId.first(), sessionManager.fetchDate.first(), GeoFencing.currentDate!!)
        }
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.tvRecycler.layoutManager = layoutManager
        binding.tvRecycler.setHasFixedSize(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_search -> {

            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.refreshImG -> {
                refreshAdapter()
            }
        }
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

    @SuppressLint("SetTextI18n")
    private fun basketResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.basketResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            binding.loader.root.isVisible = true
                            binding.loader.tvTitle.text = it.throwable!!.message.toString()
                            binding.loader.refreshImG.isVisible = true
                            binding.loader.subTitles.text = "Tape to Refresh"
                            binding.loader.imageLoader.isVisible = true
                            binding.tvRecycler.isVisible = false
                        }

                        is NetworkResult.Loading -> {
                            binding.loader.root.isVisible = true
                            binding.loader.tvTitle.text = "Connecting to MTx Cloud"
                            binding.loader.refreshImG.isVisible = false
                            binding.loader.subTitles.text = "Please Wait"
                            binding.loader.imageLoader.isVisible = true
                            binding.tvRecycler.isVisible = false

                        }

                        is NetworkResult.Success -> {
                            binding.progressbarHolder.isVisible = false
                            binding.loader.root.isVisible = false
                            binding.tvRecycler.isVisible = true
                            adapter = SalesEntryAdapter(it.data!!.data!!, applicationContext, ::handlesAdapterEvent)
                            adapter.notifyDataSetChanged()
                            binding.tvRecycler.setItemViewCacheSize(it.data!!.data!!.size)
                            binding.tvRecycler.adapter = adapter
                        }
                    }
                }
            }
        }
    }

    private  fun handlesAdapterEvent(item: BasketLimitList, binding: SalesEntryAdapterBinding) {

        var trasformPricing = 0
        var trasformInventory = 0.0
        var trasformOrder = 0.0
        var controltrasformPricing = 0
        var controltrasformInventory = 0
        var controltrasformOrder = 0

        if (binding.mtPricing.text.toString().isNotEmpty()) {
            trasformPricing = binding.mtPricing.text.toString().toInt()
            controltrasformPricing = 1
        }

        if(binding.mtInventory.text.toString()==".") {
            binding.mtInventory.setText("")
            controltrasformInventory = 0
        }else if (binding.mtInventory.text.toString().isNotEmpty()) {
            trasformInventory = binding.mtInventory.text.toString().toDouble()
            controltrasformInventory = 1
        }

        if(binding.mtOrder.text.toString()==".") {
            binding.mtOrder.setText("")
            controltrasformOrder = 0
        }else if (binding.mtOrder.text.toString().isNotEmpty()) {
            trasformOrder = binding.mtOrder.text.toString().toDouble()
            if(trasformOrder > item.order_sold!!) {

                binding.mtOrder.setText("")
                controltrasformOrder = 0

            }else{
                controltrasformOrder = 1
            }
        }


    }
}