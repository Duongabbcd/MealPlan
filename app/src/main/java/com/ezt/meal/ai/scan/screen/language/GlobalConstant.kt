package com.ezt.meal.ai.scan.screen.language

import com.ezt.meal.ai.scan.screen.language.adapter.Language
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.database.Meal
import com.ezt.meal.ai.scan.model.MeasurementUnits
import com.ezt.meal.ai.scan.model.TotalNutrition


object GlobalConstant {
    fun getListLocation(): ArrayList<Language> {
        val listLanguage: ArrayList<Language> = ArrayList()
        listLanguage.add(Language(R.drawable.english, "English", "en"))
        listLanguage.add(Language(R.drawable.arabic, "العربية", "ar"))
        listLanguage.add(Language(R.drawable.bengali, "বাংলা", "bn"))
        listLanguage.add(Language(R.drawable.german, "Deutsch", "de"))
        listLanguage.add(Language(R.drawable.spanish, "Español", "es"))
        listLanguage.add(Language(R.drawable.french, "Français", "fr"))
        listLanguage.add(Language(R.drawable.hindi, "हिन्दी", "hi"))
        listLanguage.add(Language(R.drawable.indonesian, "Bahasa", "in"))
        listLanguage.add(Language(R.drawable.portuguese, "Português", "pt"))
        listLanguage.add(Language(R.drawable.italia, "Italiano", "it"))
        listLanguage.add(Language(R.drawable.russia, "Русский", "ru"))
        listLanguage.add(Language(R.drawable.korean, "한국어", "ko"))

        return listLanguage
    }


    fun getSearchMeals() : ArrayList<Meal> {
        val listMeals: ArrayList<Meal> = ArrayList()
        listMeals.add(Meal( dishName = "Pizza Margherita",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 270.0, liquids = 0.0, estimatedAmount = "270.0 g", carbs = 34.0, fats = 9.0, proteins = 12.0, calories = 270.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Cheeseburger",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 450.0, liquids = 0.0, estimatedAmount = "450.0 g", carbs = 40.0, fats = 22.0, proteins = 25.0, calories = 450.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Sushi (Salmon Roll)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 300.0, liquids = 0.0, estimatedAmount = "300.0 g", carbs = 45.0, fats = 6.0, proteins = 12.0, calories = 300.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))


        listMeals.add(Meal( dishName = "Pad Thai",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 400.0, liquids = 0.0, estimatedAmount = "400.0 g", carbs = 34.0, fats = 9.0, proteins = 12.0, calories = 400.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))


        listMeals.add(Meal( dishName = "Pho Bo",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 350.0, liquids = 100.0, estimatedAmount = "270.0 g", carbs = 22.0, fats = 52.0, proteins = 6.0, calories = 350.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))


        listMeals.add(Meal( dishName = "Chicken Curry",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 270.0, liquids = 0.0, estimatedAmount = "270.0 g", carbs = 34.0, fats = 9.0, proteins = 12.0, calories = 270.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))


        listMeals.add(Meal( dishName = "Fish and Chips",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 550.0, liquids = 0.0, estimatedAmount = "550.0 g", carbs = 60.0, fats = 25.0, proteins = 20.0, calories = 550.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))


        listMeals.add(Meal( dishName = "Caesar Salad",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 360.0, liquids = 0.0, estimatedAmount = "360.0 g", carbs = 32.0, fats = 8.0, proteins = 10.0, calories = 360.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Spaghetti Carbonara",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 480.0, liquids = 0.0, estimatedAmount = "480.0 g", carbs = 65.0, fats = 16.0, proteins = 18.0, calories = 480.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Tacos (Beef)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 24.0, fats = 18.0, proteins = 16.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Lasagna",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 420.0, liquids = 0.0, estimatedAmount = "420.0 g", carbs = 38.0, fats = 19.0, proteins = 22.0, calories = 420.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Fried Rice (chicken)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 24.0, fats = 18.0, proteins = 16.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Ramen",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 460.0, liquids = 0.0, estimatedAmount = "460.0 g", carbs = 60.0, fats = 18.0, proteins = 15.0, calories = 460.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Dim Sum (Shrimp Dumpling)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 160.0, liquids = 0.0, estimatedAmount = "160.0 g", carbs = 20.0, fats = 4.0, proteins = 8.0, calories = 160.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Banh Mi (Pork)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 400.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 50.0, fats = 14.0, proteins = 18.0, calories = 400.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Steak (Ribeye 200g)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 580.0, liquids = 0.0, estimatedAmount = "580.0 g", carbs = 0.0, fats = 44.0, proteins = 45.0, calories = 580.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Burrito (Bean & Cheese)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 480.0, liquids = 0.0, estimatedAmount = "480.0 g", carbs = 65.0, fats = 18.0, proteins = 16.0, calories = 480.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Pancakes (with Syrup)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 350.0, liquids = 0.0, estimatedAmount = "350.0 g", carbs = 70.0, fats = 8.0, proteins = 6.0, calories = 350.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Tom Yum Soup",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 150.0, liquids = 0.0, estimatedAmount = "150.0 g", carbs = 10.0, fats = 8.0, proteins = 12.0, calories = 150.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Fried Chicken (2 pcs)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 24.0, fats = 18.0, proteins = 16.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Grilled Cheese Sandwich",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 30.0, fats = 24.0, proteins = 12.0, calories = 380.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Kimchi Stew (Kimchi-jjigae)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 280.0, liquids = 0.0, estimatedAmount = "280.0 g", carbs = 15.0, fats = 16.0, proteins = 18.0, calories = 280.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Falafel Sandwich",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 550.0, liquids = 0.0, estimatedAmount = "550.0 g", carbs = 55.0, fats = 20.0, proteins = 14.0, calories = 550.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Biryani (Chicken)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 420.0, liquids = 0.0, estimatedAmount = "420.0 g", carbs = 60.0, fats = 12.0, proteins = 22.0, calories = 420.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Croissant",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 260.0, liquids = 0.0, estimatedAmount = "260.0 g", carbs = 28.0, fats = 14.0, proteins = 5.0, calories = 260.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Donut (Glazed)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 250.0, liquids = 0.0, estimatedAmount = "250.0 g", carbs = 30.0, fats = 14.0, proteins = 3.0, calories = 250.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "French Fries (Medium)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 24.0, fats = 18.0, proteins = 16.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Greek Salad",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 280.0, liquids = 0.0, estimatedAmount = "280.0 g", carbs = 12.0, fats = 22.0, proteins = 8.0, calories = 280.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Hot Dog",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 290.0, liquids = 0.0, estimatedAmount = "290.0 g", carbs = 24.0, fats = 16.0, proteins = 10.0, calories = 290.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Macaroni and Cheese",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 400.0, liquids = 0.0, estimatedAmount = "400.0 g", carbs = 45.0, fats = 18.0, proteins = 14.0, calories = 400.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Spring Rolls (Fresh)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 180.0, liquids = 0.0, estimatedAmount = "180.0 g", carbs = 25.0, fats = 3.0, proteins = 10.0, calories = 180.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Tiramisu",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 350.0, liquids = 0.0, estimatedAmount = "350.0 g", carbs = 38.0, fats = 20.0, proteins = 6.0, calories = 350.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Paella",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 420.0, liquids = 0.0, estimatedAmount = "420.0 g", carbs = 55.0, fats = 12.0, proteins = 22.0, calories = 420.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Beef Stew",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 20.0, fats = 22.0, proteins = 25.0, calories = 380.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Bagel with Cream Cheese",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 48.0, fats = 10.0, proteins = 10.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Waffles",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 310.0, liquids = 0.0, estimatedAmount = "310.0 g", carbs = 35.0, fats = 15.0, proteins = 7.0, calories = 310.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Miso Soup",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 60.0, liquids = 0.0, estimatedAmount = "60.0 g", carbs = 8.0, fats = 1.0, proteins = 4.0, calories = 60.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Peking Duck (Wrap)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 350.0, liquids = 0.0, estimatedAmount = "350.0 g", carbs = 30.0, fats = 18.0, proteins = 18.0, calories = 350.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Chili Con Carne",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 300.0, liquids = 0.0, estimatedAmount = "300.0 g", carbs = 25.0, fats = 12.0, proteins = 20.0, calories = 300.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Egg Benedict",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 450.0, liquids = 0.0, estimatedAmount = "450.0 g", carbs = 25.0, fats = 30.0, proteins = 20.0, calories = 450.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Hummus (with Pita)",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 24.0, fats = 18.0, proteins = 16.0, calories = 380.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Ratatouille",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 200.0, liquids = 0.0, estimatedAmount = "200.0 g", carbs = 45.0, fats = 18.0, proteins = 12.0, calories = 200.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Gnocchi",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 65.0, fats = 4.0, proteins = 8.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Ceviche",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 180.0, liquids = 0.0, estimatedAmount = "180.0 g", carbs = 12.0, fats = 6.0, proteins = 22.0, calories = 180.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Bibimbap",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 480.0, liquids = 0.0, estimatedAmount = "480.0 g", carbs = 75.0, fats = 10.0, proteins = 20.0, calories = 480.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Kebab",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 450.0, liquids = 0.0, estimatedAmount = "450.0 g", carbs = 40.0, fats = 20.0, proteins = 25.0, calories = 450.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        listMeals.add(Meal( dishName = "Clam Chowder",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 320.0, liquids = 0.0, estimatedAmount = "320.0 g", carbs = 22.0, fats = 20.0, proteins = 12.0, calories = 320.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Chicken Parmesan",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 550.0, liquids = 0.0, estimatedAmount = "550.0 g", carbs = 35.0, fats = 28.0, proteins = 40.0, calories = 550.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))

        listMeals.add(Meal( dishName = "Mango Sticky Rice",
            image = "",
            ingredients = listOf(),
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 65.0, fats = 12.0, proteins = 4.0, calories = 380.0),
            processingTime = 0.0,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = -1,
            status = "success",
            message = "Food nutrition analyzed successfully", date = 0L))
        return listMeals
    }

    fun getTheMealImage(dishName: String = "Pizza Margherita") : Int {
       return when(dishName) {
            "Pizza Margherita" -> R.drawable.p1
            "Cheeseburger" -> R.drawable.p2
           "Sushi (Salmon Roll)" -> R.drawable.p3
            "Pad Thai" -> R.drawable.p4
            "Pho Bo (Beef Noodle Soup)"-> R.drawable.p5
            "Chicken Curry" -> R.drawable.p6
            "Fish and Chips" -> R.drawable.p7
            "Caesar Salad" -> R.drawable.p8
            "Spaghetti Carbonara" -> R.drawable.p9
            "Tacos (Beef)" -> R.drawable.p10
            "Lasagna" -> R.drawable.p11
            "Fried Rice (chicken)" -> R.drawable.p12
            "Ramen" -> R.drawable.p13
            "Dim Sum (Shrimp Dumpling)" -> R.drawable.p14
           "Banh Mi (Pork)" -> R.drawable.p15
            "Steak (Ribeye 200g)" -> R.drawable.p16
            "Burrito (Bean & Cheese)" -> R.drawable.p17
             "Pancakes (with Syrup)" -> R.drawable.p18
             "Tom Yum Soup"-> R.drawable.p19
            "Fried Chicken (2 pcs)" -> R.drawable.p20
            "Grilled Cheese Sandwich" -> R.drawable.p21
            "Kimchi Stew (Kimchi-jjigae)" -> R.drawable.p22
            "Falafel Sandwich" -> R.drawable.p23
            "Biryani (Chicken)" -> R.drawable.p24
            "Croissant" -> R.drawable.p25
            "Donut (Glazed)" -> R.drawable.p26
            "French Fries (Medium)" -> R.drawable.p27
            "Greek Salad"-> R.drawable.p28
            "Butter Chicken" -> R.drawable.p29
            "Hot Dog" -> R.drawable.p30
            "Macaroni and Cheese" -> R.drawable.p31
            "Spring Rolls (Fresh)" -> R.drawable.p32
            "Tiramisu" -> R.drawable.p33
            "Paella" -> R.drawable.p34
            "Beef Stew" -> R.drawable.p35
            "Bagel with Cream Cheese" -> R.drawable.p36
            "Waffles" -> R.drawable.p37
            "Miso Soup" -> R.drawable.p38
            "Peking Duck (Wrap)" -> R.drawable.p39
            "Chili Con Carne" -> R.drawable.p40
            "Egg Benedict" -> R.drawable.p41
            "Hummus (with Pita)" -> R.drawable.p42
            "Ratatouille" -> R.drawable.p43
            "Gnocchi" -> R.drawable.p44
            "Ceviche" -> R.drawable.p45
            "Bibimbap" -> R.drawable.p46
            "Kebab" -> R.drawable.p47
            "Clam Chowder" -> R.drawable.p48
            "Chicken Parmesan" -> R.drawable.p49
            "Mango Sticky Rice" -> R.drawable.p50
            else -> R.drawable.p1
        }
    }
}