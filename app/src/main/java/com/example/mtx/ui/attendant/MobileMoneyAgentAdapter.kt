package com.example.mtx.ui.attendant


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.mtx.databinding.MoneyAgentBinding
import com.example.mtx.dto.IsMoneyAgent
import com.example.mtx.util.StartGoogleMap
import kotlin.reflect.KFunction1


class MobileMoneyAgentAdapter (private var iSAgent: List<IsMoneyAgent>, private var context: Context,
                               private val isReturnFunction: KFunction1<IsMoneyAgent, Unit>
                               ): RecyclerView.Adapter<MobileMoneyAgentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflaters = LayoutInflater.from(parent.context)
        val binding =   MoneyAgentBinding.inflate(layoutInflaters)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = iSAgent[p1]
        p0.bind(item, isReturnFunction)
    }

    override fun getItemCount() = iSAgent.size

    inner class ViewHolder(private val binding: MoneyAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IsMoneyAgent, clickListener: KFunction1<IsMoneyAgent,  Unit>) {

            val letter: String = item.agennama!!.substring(0, 1).toUpperCase()
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder().buildRound(letter, generator.randomColor)
            binding.idcheck.setImageDrawable(drawable)
            binding.modulecontents.text = item.agennama!!.toLowerCase().capitalize()
            binding.transType.text = item.phone
            binding.remark.text = item.address!!.toLowerCase().capitalize()

            binding.iconsImages.setOnClickListener {
                val dmode = "d".single()
                val destination = "${item.lat},${item.lng}"
                StartGoogleMap.startGoogleMapIntent(context, destination, dmode, 't')
            }

            binding.content.setOnClickListener {
                clickListener(item)
            }

        }
    }
}