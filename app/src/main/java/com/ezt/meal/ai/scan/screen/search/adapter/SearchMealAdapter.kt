package com.ezt.meal.ai.scan.screen.search.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezt.meal.ai.scan.database.Meal
import com.ezt.meal.ai.scan.databinding.ItemSearchBinding
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.screen.detail.DetailActivity
import com.ezt.meal.ai.scan.screen.language.GlobalConstant
import com.google.gson.Gson

class SearchMealAdapter() :
    RecyclerView.Adapter<SearchMealAdapter.SearchMealViewHolder>() {
    private val input: MutableList<Meal> = mutableListOf()
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMealViewHolder {
        context = parent.context
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMealViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: SearchMealViewHolder,
        position: Int
    ) {
        holder.bind(position)

    }

    override fun getItemCount(): Int = input.size

    fun submitList(data: List<Meal>) {
        input.clear()
        input.addAll(data)
        notifyDataSetChanged()
    }

    inner class SearchMealViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val meal = input[position]
            binding.apply {
                val image = GlobalConstant.getTheMealImage(meal.dishName)
                Glide.with(context).load(image).into(searchMealImage)
                searchMealName.text = meal.dishName
                searchMealCalory.text = "${meal.totalNutrition.calories} ${meal.measurementUnits.calories}"


                root.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        val defaultMeal = NutritionResponse(
                            dishName = meal.dishName,
                            ingredients = meal.ingredients,
                            totalNutrition = meal.totalNutrition,
                            healthyScore = meal.healthyScore,
                            measurementUnits = meal.measurementUnits,
                            processingTime = meal.processingTime,
                            message = "",
                            status = "",
                        )

                        val imagePath = meal.image
                        putExtra("imagePath", imagePath)
                        putExtra("imageDrawable", meal.dishName)
                        putExtra("defaultMeal", Gson().toJson(defaultMeal))
                    })
                }
            }
        }
    }
}