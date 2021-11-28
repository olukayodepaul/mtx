package com.example.mtx.ui.salesrecord

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivitySalesRecordBinding
import com.example.mtx.dto.GetRequestToken
import com.example.mtx.dto.IsParcelable
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@AndroidEntryPoint
class SalesRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalesRecordBinding

    private val viewModel: SalesRecordViewModel by viewModels()

    private lateinit var adapter: SalesRecordAdapter

    private lateinit var database: FirebaseDatabase

    private lateinit var isIntentData: IsParcelable

    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        database = FirebaseDatabase.getInstance()
        sessionManager = SessionManager(this)
        isIntentData = intent.extras!!.getParcelable("isParcelable")!!
        initWidget()
        initRecyclerView()
        viewModel.fetchSalesRecordEntries()
        requestToken()
        fetchSalesRecordEntriesResponse()
        setRequestedToken()
    }

    private fun initWidget() = lifecycleScope.launchWhenCreated {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun requestToken() = lifecycleScope.launchWhenCreated{
        viewModel.tokenRecordEntries(isIntentData.data!!.urno!!, sessionManager.fetchEmployeeId.first(), "${isIntentData.latitude},${isIntentData.longitude}", sessionManager.fetchRegion.first())
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_outlet, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit -> {
                binding.cintents.isVisible = false
                binding.tokens.isVisible = true
                binding.notification.isVisible = false
            }
        }
        return false
    }

    private fun fetchSalesRecordEntriesResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.salesRecordResponseState.collect {
                it.let {
                    when (it) {
                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                        }

                        is NetworkResult.Loading -> {
                        }

                        is NetworkResult.Success -> {

                            val limitToSalesEntry = it.data!!.filter { filters ->
                                filters.seperator.equals("1")
                            }

                            val isPricing = limitToSalesEntry.sumByDouble { qty ->
                                qty.pricing!!.toDouble()
                            }

                            val isInventory = limitToSalesEntry.sumByDouble { price ->
                                price.inventory!!.toDouble()
                            }

                            val isQtyOrdered = limitToSalesEntry.sumByDouble { price ->
                                price.orders!!.toDouble()
                            }

                            val isAmount = limitToSalesEntry.sumByDouble { price ->
                                price.orders!!.toDouble() * price.price!!
                            }

                            binding.vamount.text = NumberFormat.getInstance().format(isAmount)
                            binding.vinventory.text = NumberFormat.getInstance().format(isInventory)
                            binding.vpricing.text = NumberFormat.getInstance().format(isPricing)
                            binding.vqtysold.text = NumberFormat.getInstance().format(isQtyOrdered)

                            adapter = SalesRecordAdapter(limitToSalesEntry)
                            adapter.notifyDataSetChanged()
                            binding.recycler.setItemViewCacheSize(limitToSalesEntry.size)
                            binding.recycler.adapter = adapter
                        }
                    }
                }
            }
        }
    }

    private fun setRequestedToken() {
        val references =    database.getReference("/defaulttoken/${isIntentData.data!!.urno}}")
        references.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val vToken = p0.getValue(GetRequestToken::class.java)
                    binding.tvFieldCustname.setText(vToken!!.token)
                }
            }
        })
    }

}