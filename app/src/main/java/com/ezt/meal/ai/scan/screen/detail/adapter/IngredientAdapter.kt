package com.ezt.meal.ai.scan.screen.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.databinding.ItemIngredientBinding
import com.ezt.meal.ai.scan.model.Ingredient
import java.io.DataInput

class IngredientAdapter(private val input: MutableList<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: IngredientViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = input.size

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val ingredient = input[position]
            binding.apply {
                name.text = "●".plus(" ${ingredient.foodName}: ")
                amount.text = ingredient.estimatedAmount.toString().plus(" ${ingredient.unit}")
            }
        }

    }
}