package com.ezt.meal.ai.scan.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.ItemDateBinding
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

class RecentDateAdapter(
    private val onSelectDate: (RecentDate) -> Unit
) : ListAdapter<RecentDate, RecentDateAdapter.RecentDateViewHolder>(DIFF_UTIL) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDateViewHolder {
        context = parent.context
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentDateViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun setSelectedPosition(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        if (previous != RecyclerView.NO_POSITION) notifyItemChanged(previous)
        notifyItemChanged(position)
    }

    inner class RecentDateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: RecentDate, position: Int) {
            binding.apply {
                val monthName = Month.of(date.month)
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault())
                recentMonth.text = monthName
                recentDay.text = date.day.toString()

                val isSelected = position == selectedPosition

                root.setBackgroundResource(
                    if (isSelected)
                        R.drawable.background_selected_radius_18
                    else
                        R.drawable.background_unselected_radius_18
                )
                recentDay.setTextColor(
                    if (isSelected)
                        context.getColor(R.color.black)
                    else
                        context.getColor(R.color.unselected)
                )

                root.setOnClickListener {
                    select(position)
                }
            }
        }

        private fun select(position: Int) {
            val previous = selectedPosition

            if (position == selectedPosition) {
                selectedPosition = RecyclerView.NO_POSITION
                notifyItemChanged(position)
                onSelectDate(RecentDate.RECENT_DATE_DEFAULT)
                return
            }

            selectedPosition = position

            if (previous != RecyclerView.NO_POSITION) notifyItemChanged(previous)
            notifyItemChanged(position)
            onSelectDate(getItem(position))
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<RecentDate>() {
            override fun areItemsTheSame(oldItem: RecentDate, newItem: RecentDate): Boolean {
                return oldItem.day == newItem.day && oldItem.month == newItem.month
            }

            override fun areContentsTheSame(oldItem: RecentDate, newItem: RecentDate): Boolean {
                return oldItem == newItem
            }
        }
    }
}


data class RecentDate(
    val year: Int,
    val month:Int ,
    val day: Int,
    var isSelected: Boolean = false
) {
    companion object {
        val RECENT_DATE_DEFAULT = RecentDate(-1, -1, -1, false)
    }
}