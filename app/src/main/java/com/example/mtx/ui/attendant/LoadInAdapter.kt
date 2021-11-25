package com.example.mtx.ui.attendant

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtx.databinding.LoadoutAdapterBinding
import com.example.mtx.dto.BasketLimitList


class LoadInAdapter(private var mItems: List<BasketLimitList>) :
    RecyclerView.Adapter<LoadInAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = LoadoutAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: LoadoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BasketLimitList) {
            binding.sku.text = item.product_name!!.toUpperCase().capitalize()
            binding.qty.text = NumberFormat.getInstance().format(item.qty)
            binding.amount.text = NumberFormat.getInstance().format(item.price)
            binding.total.text = NumberFormat.getInstance().format(item.qty!!.toDouble() * item.price!!.toDouble())
        }
    }

}
