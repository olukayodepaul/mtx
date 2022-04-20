package com.example.mtx.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.dto.AllCustomerProductOrder
import kotlin.reflect.KFunction2
import com.example.mtx.databinding.OrderAdapterBinding

class OrderAdapter(
    private var mItems: List<AllCustomerProductOrder>,
    private val isReturnFunction: KFunction2<AllCustomerProductOrder, OrderAdapterBinding, Unit>
) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =  OrderAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item, isReturnFunction)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: OrderAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AllCustomerProductOrder, clickListener: KFunction2<AllCustomerProductOrder, OrderAdapterBinding, Unit>) {

            val letter: String? = item.outletname.substring(0, 1)
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)

            binding.IdCheck.setImageDrawable(drawable)
            binding.modulecontents.text = item.outletname
            binding.remark.text = ("URNO: ${item.urno}")
            binding.timeago.text = "${item.dates} ${item.trantime}"
            binding.transType.text = "Cash Payment"

            if(item.trantype.toLowerCase()=="true"){
                binding.transType.text = "Paid"
            }

            binding.iconsImages.setOnClickListener {
                clickListener(item, binding)
            }

        }
    }
}