package com.example.mtx.ui.sales

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.R
import com.example.mtx.databinding.SalesAdapterBinding
import com.example.mtx.dto.CustomersList
import kotlin.reflect.KFunction3

class SalesAdapter(private var mItems: List<CustomersList>, private val context: Context,
                   private val isReturnFunction: KFunction3<CustomersList, Int,SalesAdapterBinding,  Unit>) :
    RecyclerView.Adapter<SalesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = SalesAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item, isReturnFunction)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: SalesAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: CustomersList, clickListener: KFunction3<CustomersList, Int, SalesAdapterBinding,  Unit>
        ) {

            val letter: String = item.outletname!!.substring(0, 1).toUpperCase()
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)
            binding.IdCheck .setImageDrawable(drawable)
            binding.modulecontents.text = item.outletname!!.toLowerCase().capitalize()
            binding.remark.text = ("URNO: ${item.urno}, VCL: ${item.volumeclass}")
            binding.timeago.text = item.timeago

            if(item.sort==1) {
                binding.iconsImages.isVisible = false
                binding.modulecontents.text = item.notice
            }

            if(item.sort==2) {
                binding.custIcons.isVisible = false
            }

            if(item.sort==3) {
                binding.iconsImages.isVisible = false
                binding.modulecontents.text = item.notice
            }

            if(item.sort==4) {
                binding.iconsImages.isVisible = false
                binding.modulecontents.text = item.notice
            }

            binding.iconsImages.setOnClickListener {
                showPopup(binding, item, clickListener)
            }
        }

        private fun showPopup(
            binding: SalesAdapterBinding,
            item: CustomersList,
            clickItems: KFunction3<CustomersList, Int, SalesAdapterBinding, Unit>
        ){
            val popupMenu = PopupMenu(context, binding.iconsImages)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.floatingcontextmenu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.entries_id -> {
                        clickItems(item, 1, binding)
                    }
                    R.id.outlet_nav -> {
                        clickItems(item, 2, binding)
                    }
                    R.id.update_outlet -> {
                        clickItems(item, 2, binding)
                    }
                    R.id.close_outlet -> {
                        clickItems(item, 4, binding)
                    }
                    R.id.async -> {
                        clickItems(item, 5, binding)
                    }
                    R.id.details -> {
                        clickItems(item, 6, binding)
                    }
                    R.id.outlet_status -> {
                        clickItems(item, 7, binding)
                    }
                }
                true
            }
            popupMenu.show()
        }
    }
}