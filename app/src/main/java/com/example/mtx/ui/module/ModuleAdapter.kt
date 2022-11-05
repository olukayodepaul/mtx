package com.example.mtx.ui.module

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.databinding.UserModulesAdapterBinding
import com.example.mtx.dto.UserModules
import com.example.mtx.ui.attendant.MapMobileAgent
import com.example.mtx.ui.messages.MessageActivity
import com.example.mtx.ui.order.ReOrderActivity
import com.example.mtx.ui.sales.SalesActivity


class ModuleAdapter(private var mItems: List<UserModules>, private val context: Context) :
    RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding = UserModulesAdapterBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder(private val binding: UserModulesAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserModules) {

            val letter: String = item.name!!.substring(0, 1).toUpperCase()
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)
            binding.idCheck.setImageDrawable(drawable)
            binding.modulecontents.text = item.name!!.lowercase().capitalize()
            binding.remark.text = item.imageurl!!.lowercase().capitalize()

            binding.parentModules.setOnClickListener {
                when (item.navigationid) {
                    1 -> {
                        val intent = Intent(context, SalesActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(context, MessageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                    6 -> {
                        val intent = Intent(context, ReOrderActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                    7-> {
                        val intent = Intent(context, MapMobileAgent::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                }
            }

        }
    }
}