package com.example.mtx.ui.salesrecord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.SalesrecodAdapterBinding
import com.example.mtx.dto.BasketLimitList


class SalesRecordAdapter(private var mItems: List<BasketLimitList>) :
    RecyclerView.Adapter<SalesRecordAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =  SalesrecodAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: SalesrecodAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BasketLimitList) {


            val sum = item.orders!!.toFloat() * item.price!!
            binding.tvSkuss.text = item.product_name!!.toLowerCase().capitalize()
            binding.mtInventorys.text = "${item.inventory}"
            binding.mtPricings.text = "${item.pricing}"
            binding.mtOrders.text = "${item.orders}"
            binding.mtAmounts.text =  "$sum"

            }
        }
}
