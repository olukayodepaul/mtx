package com.example.mtx.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivityReOrderBinding
import com.example.mtx.databinding.OrderAdapterBinding
import com.example.mtx.dto.AllCustomerProductOrder
import com.example.mtx.util.FirebaseDatabases.setOrderBadge
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.StartGoogleMap.startGoogleMapIntent
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.notificationbadge.NotificationBadge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ReOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReOrderBinding

    private lateinit var adapter: OrderAdapter

    private val viewModel: OrderViewModel by viewModels()

    private lateinit var database: FirebaseDatabase

    var notificationBadgeView: View? = null

    var notificationBadge: NotificationBadge? = null

    var item_Notification: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        database = FirebaseDatabase.getInstance()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initAdapter()
        viewModel.fetchAllSalesEntries(193)
        isAllOrderRequest()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.tvRecycler.layoutManager = layoutManager
        binding.tvRecycler.setHasFixedSize(true)
    }

    private fun isAllOrderRequest() = lifecycleScope.launchWhenResumed {
        viewModel.orderResponseState.collect {
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
                        //check empty list
                        if(it.data!!.order.isNullOrEmpty()){
                            //is empty message....No Order Assign for this rep
                        }else{
                            adapter = OrderAdapter(it.data.order!!, ::handleAdapterEvent)
                            adapter.notifyDataSetChanged()
                            binding.tvRecycler.setItemViewCacheSize(it.data.order!!.size)
                            binding.tvRecycler.adapter = adapter
                        }

                    }
                }
            }
        }
    }

    private fun handleAdapterEvent(
        item: AllCustomerProductOrder,
        isAdapterBinding: OrderAdapterBinding
    ) {

        val popupMenu = PopupMenu(this, isAdapterBinding.iconsImages)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.floatingordermenu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.googlelocation -> {
                    startGoogleMapIntent(applicationContext,  "${item.latitude},${item.longitude}", "l".single(), 't')
                }
                R.id.deliver -> {
                    val intent = Intent(this, OrderSummary::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("isParcelable", item)
                    startActivity(intent)
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visitdetail, menu)
        item_Notification = menu!!.findItem(R.id.action_notifications)
        notificationBadgeView = item_Notification!!.actionView
        notificationBadge = notificationBadgeView!!.findViewById(R.id.badge) as NotificationBadge
        setOrderBadge(193, database, notificationBadge)
        return true
    }

}