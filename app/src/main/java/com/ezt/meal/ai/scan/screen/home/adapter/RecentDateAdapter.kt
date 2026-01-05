package com.ezt.meal.ai.scan.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.ItemDateBinding

class RecentDateAdapter(private val onSelectDate: (RecentDate) -> Unit) :
    RecyclerView.Adapter<RecentDateAdapter.RecentDateViewHolder>() {
    private val input: MutableList<RecentDate> = mutableListOf()
    private lateinit var context: Context
    private var selectedPosition: Int = RecyclerView.NO_POSITION


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentDateViewHolder {
        context = parent.context

            val binding =
                ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentDateViewHolder(binding)


    }

    override fun onBindViewHolder(
        holder:  RecentDateViewHolder,
        position: Int
    ) {
        holder.bind(position)

    }

    override fun getItemCount(): Int = input.size

    fun submitList(data: List<RecentDate>) {
        input.clear()
        input.addAll(data)
        notifyDataSetChanged()
    }

    inner class  RecentDateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val date = input[position]
            binding.apply {
                recentMonth.text = date.month.substring(0,2)
                recentDay.text = date.day.toString()

                if(date.isSelected) {
                    root.setBackgroundResource(R.drawable.background_selected_radius_18)
                    recentDay.setTextColor(context.resources.getColor(R.color.black))
                } else {
                    root.setBackgroundResource(R.drawable.background_unselected_radius_18)
                    recentDay.setTextColor(context.resources.getColor(R.color.unselected))


                }

                root.setOnClickListener {
                    val previousPosition = selectedPosition
                    println("position: $position and $selectedPosition")
                    // 🔁 Click same item again → unselect
                    if (position == selectedPosition) {
                        date.isSelected = false
                        selectedPosition = RecyclerView.NO_POSITION

                        notifyItemChanged(position)
                        onSelectDate(RecentDate.RECENT_DATE_DEFAULT)
                        return@setOnClickListener
                    }

                    // 🔄 Click different item
                    if (previousPosition != RecyclerView.NO_POSITION) {
                        input[previousPosition].isSelected = false
                        notifyItemChanged(previousPosition)
                    }

                    date.isSelected = true
                    selectedPosition = position
                    notifyItemChanged(position)

                    // ✅ ALWAYS return newest selected date
                    onSelectDate(date)
                }
            }
        }

    }
}

data class RecentDate(
    val month:String ,
    val day: Int,
    var isSelected: Boolean = false
) {
    companion object {
        val RECENT_DATE_DEFAULT = RecentDate("0", 0, false)
    }
}