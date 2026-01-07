package com.ezt.meal.ai.scan.screen.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezt.meal.ai.scan.database.Meal
import com.ezt.meal.ai.scan.databinding.ItemRecentBinding
import com.ezt.meal.ai.scan.databinding.ItemRecentHomeBinding
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.screen.detail.DetailActivity
import com.google.gson.Gson

class RecentMealAdapter(private val isHome: Boolean = true) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val input: MutableList<Meal> = mutableListOf()
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        context = parent.context

        if(isHome) {
            val binding =
                ItemRecentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentHomeMealViewHolder(binding)
        } else {
            val binding =
                ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentMealViewHolder(binding)
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        if(holder is RecentHomeMealViewHolder) {
            holder.bind(position)
        }

         else if(holder is RecentMealViewHolder) {
             holder.bind(position)
        }

    }

    override fun getItemCount(): Int = input.size

    fun submitList(data: List<Meal>) {
        input.clear()
        input.addAll(data)
        notifyDataSetChanged()
    }

    inner class RecentMealViewHolder(private val binding: ItemRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val meal = input[position]
            binding.apply {
                println("RecentMealViewHolder: ${meal.date}")
                Glide.with(context).load(meal.image).into(recentMealImage)
                recentMealName.text = meal.dishName
                recentMealCalory.text = meal.totalNutrition.calories.toString().plus("Kcal") +
                        " - " + meal.totalNutrition.solids.toString().plus("g")

                proteinValue.text = meal.totalNutrition.proteins.toString().plus("${meal.measurementUnits.proteins}")
                carbValue.text = meal.totalNutrition.carbs.toString().plus("${meal.measurementUnits.carbs}")
                fatValue.text = meal.totalNutrition.fats.toString().plus("${meal.measurementUnits.fats}")


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
                        putExtra("defaultMeal", Gson().toJson(defaultMeal))
                        putExtra("mealDate", meal.date)
                        putExtra("rotation", meal.isFront)
                    })
                }
            }
        }

    }

    inner class RecentHomeMealViewHolder(private val binding: ItemRecentHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val meal = input[position]
            binding.apply {
                println("RecentHomeMealViewHolder: ${meal.date}")
                Glide.with(context).load(meal.image).into(recentMealImage)
                recentMealName.text = meal.dishName
                recentMealCalory.text = meal.totalNutrition.calories.toString().plus("Kcal") +
                        " - " + meal.totalNutrition.solids.toString().plus("g")

                proteinValue.text = meal.totalNutrition.proteins.toString().plus("${meal.measurementUnits.proteins}")
                carbValue.text = meal.totalNutrition.carbs.toString().plus("${meal.measurementUnits.carbs}")
                fatValue.text = meal.totalNutrition.fats.toString().plus("${meal.measurementUnits.fats}")


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
                        putExtra("defaultMeal", Gson().toJson(defaultMeal))
                        putExtra("mealDate", meal.date)
                        putExtra("rotation", meal.isFront)
                    })
                }
            }
        }

    }
}