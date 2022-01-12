package com.example.mtx.ui.orderpurchase


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.databinding.ItemRowParentBinding
import com.example.mtx.dto.Orders



open class OrderAdapter(private var context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var mItems: List<Orders> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =   ItemRowParentBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item, p1)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(val binding: ItemRowParentBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Orders, p1: Int) {

            binding.modulecontents.text = item.etime

            if(item.remark!!.isNotEmpty()) {
                val letter: String = item.remark!!.substring(0, 1).toUpperCase()
                val generator = ColorGenerator.MATERIAL
                val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)
                binding.IdCheck.setImageDrawable(drawable)
            }

            if(item.order!!.isNotEmpty()) {
                val itemListAdapter = OrderItemAdapter(item.order!!)
                val layoutManager = LinearLayoutManager(itemView.context)
                binding.recyclerItems.layoutManager = layoutManager
                binding.recyclerItems.adapter = itemListAdapter
            }

            val isExpandable: Boolean = item.expandable!!

            binding.hostExpandable.isVisible = isExpandable

            if(isExpandable){
                binding.parentModules.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }else{
                binding.parentModules.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            val limitToSalesEntry = item.order!!.filter {totals->
                totals.seperatorname.equals("own brands")
            }

            val isPricing = limitToSalesEntry.sumByDouble { pricing->
                pricing.pricing!!.toDouble()
            }

            val isInventory = limitToSalesEntry.sumByDouble { inventory->
                inventory.inventory!!.toDouble()
            }

            val isOrder = limitToSalesEntry.sumByDouble { order->
                order.orders!!.toDouble()
            }

            binding.qtys.text =  NumberFormat.getInstance().format(isPricing)
            binding.amounts.text = NumberFormat.getInstance().format(isInventory)
            binding.totals.text = NumberFormat.getInstance().format(isOrder)

            binding.parentModules.setOnClickListener {
                item.expandable = !item.expandable!!
                notifyItemChanged(p1)
            }
        }
     }

    fun addData(list: List<Orders>) {
        mItems = list
        notifyDataSetChanged()
    }

}