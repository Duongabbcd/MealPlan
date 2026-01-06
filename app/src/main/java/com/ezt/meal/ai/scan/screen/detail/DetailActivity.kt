package com.ezt.meal.ai.scan.screen.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.database.Meal
import com.ezt.meal.ai.scan.databinding.ActivityDetailBinding
import com.ezt.meal.ai.scan.model.Ingredient
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.detail.adapter.IngredientAdapter
import com.ezt.meal.ai.scan.screen.language.GlobalConstant
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.utils.Common.visible
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.round

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate) {
    private val defaultMeal by lazy {
        val input = intent.getStringExtra("defaultMeal")
        Gson().fromJson(input, NutritionResponse::class.java)
    }

    private val imagePath by lazy {
        intent.getStringExtra("imagePath") ?: ""
    }
    private val imageDrawable by lazy {
        intent.getIntExtra("imageDrawable", 0)
    }
    private val mealDate by lazy {
        intent.getLongExtra("mealDate", -1L)
    }


    private val isDetected by lazy {
        intent.getBooleanExtra("isDetected", false)
    }
    private val totalNutrition by lazy {
        defaultMeal.totalNutrition
    }
    private val input by lazy {
        defaultMeal.ingredients.toMutableList()
    }

    private val totalCalories by lazy {
        totalNutrition.calories
    }

    private val measuredUnit by lazy {
        defaultMeal.measurementUnits
    }

    private lateinit var ingredientAdapter: IngredientAdapter
    private val mealViewModel: MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            println("defaultMeal: $defaultMeal")

            backBtn.setOnClickListener {
                finish()
            }

            if (imagePath.isNotEmpty()) {
                Glide.with(this@DetailActivity).load(imagePath).transform(RotateTransformation(90F))
                    .into(binding.mealBg1)

            } else {
                binding.mealBg1.setImageResource(GlobalConstant.getTheMealImage(imageDrawable))
            }

            appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                val isExpand = abs(verticalOffset) < appBarLayout.totalScrollRange

                if (isExpand) {
                    binding.mealBg1.visible()
                    binding.mealBg.gone()
                } else {
                    binding.mealBg.setImageResource(R.color.white)
                    binding.mealBg1.gone()
                    binding.mealBg.visible()
                }

                backBtn.setImageResource(if (isExpand) R.drawable.icon_back_white else R.drawable.icon_back_black)
                share.setImageResource(if (isExpand) R.drawable.icon_share_detail else R.drawable.icon_share_detail_black)
            }

            input.add(
                Ingredient(
                    foodName = resources.getString(R.string.total_serve),
                    estimatedAmount = totalCalories,
                    unit = "g",
                    carbs = 0.0,
                    fats = 0.0,
                    proteins = 0.0,
                    calories = 0.0
                )
            )
            ingredientAdapter = IngredientAdapter(input)



            mealName.text = defaultMeal.dishName
            totalCalo.text = totalNutrition.calories.toInt().toString()
            kcal.text = measuredUnit.calories

            circleChart.proteinPercent = (totalNutrition.proteins / totalCalories).toFloat()
            circleChart.carbPercent = (totalNutrition.carbs / totalCalories).toFloat()
            circleChart.fatPercent = (totalNutrition.fats / totalCalories).toFloat()
            circleChart.invalidate()

            val proteinProcess = totalNutrition.proteins * 100 / totalCalories
            proteinPercent.text = round(proteinProcess * 100 / 100).toString().plus(" %")
            proteinValue.text = totalNutrition.proteins.toString().plus(" ${measuredUnit.proteins}")
            proteinProgress.progress = proteinProcess.toInt()

            val carbProcess = totalNutrition.carbs * 100 / totalCalories
            carbPercent.text = round(carbProcess * 100 / 100).toString().plus(" %")
            carbValue.text = totalNutrition.carbs.toString().plus(" ${measuredUnit.carbs}")
            carbProgress.progress = carbProcess.toInt()

            val fatProcess = totalNutrition.fats * 100 / totalCalories
            fatPercent.text = round(fatProcess * 100 / 100).toString().plus(" %")
            fatValue.text = totalNutrition.fats.toString().plus(" ${measuredUnit.fats}")
            fatProgress.progress = fatProcess.toInt()

            applyStyle(
                mealDetailName,
                resources.getString(R.string.meal_name),
                defaultMeal.dishName
            )
            applyStyle(
                mealDetailIngredients,
                resources.getString(R.string.meal_ingredient),
                defaultMeal.ingredients.joinToString(", ") {
                    it.foodName
                })

            allIngredients.adapter = ingredientAdapter
            applyStyle(
                mealEnergy,
                resources.getString(R.string.meal_energy),
                totalNutrition.calories.toInt().toString().plus(" ${measuredUnit.calories}")
            )

            carbonValue.text = totalNutrition.carbs.toString().plus(" ${measuredUnit.carbs}")
            proteinTotalValue.text =
                totalNutrition.proteins.toString().plus(" ${measuredUnit.proteins}")
            fatTotalValue.text = totalNutrition.fats.toString().plus(" ${measuredUnit.fats}")
            solidValue.text = totalNutrition.solids.toString().plus(" ${measuredUnit.solids}")
            liquidValue.text = totalNutrition.liquids.toString().plus(" ${measuredUnit.liquids}")

            today.text = if(mealDate != -1L) {
                formatTimestamp(mealDate)
            } else {
                resources.getString(R.string.today).plus(", ${getCurrentTime()}")
            }

            share.setOnClickListener {
                generatePdfAndShare()
            }

            if(!isDetected) {
                saveButton.gone()
            }

            if(imagePath.isEmpty()) {
                saveButton.gone()

                mealOverview.gone()
                mealDetailName.gone()
                mealDetailType.gone()

                mealSize.gone()
                allIngredients.gone()
                mealNutrition.gone()
                mealEnergy.gone()
                mealDetailIngredients.gone()
                mealNutrition.gone()
                macroNutrients.gone()

                nutrientLayout.gone()
                carbonLayout.gone()
                proteinLayout.gone()
                fatLayout.gone()
                solidLayout.gone()
                liquidLayout.gone()
            }

            saveButton.setOnClickListener {
                mealViewModel.insertMeal(
                    Meal(
                        dishName = defaultMeal.dishName,
                        image = imagePath,
                        ingredients = defaultMeal.ingredients,
                        totalNutrition = defaultMeal.totalNutrition,
                        healthyScore = defaultMeal.healthyScore,
                        measurementUnits = defaultMeal.measurementUnits,
                        processingTime = defaultMeal.processingTime,
                        status = defaultMeal.status,
                        message = defaultMeal.message,
                        date = System.currentTimeMillis()
                    )
                )
                Toast.makeText(
                    this@DetailActivity,
                    resources.getString(R.string.saved_successfully),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun getCurrentTime(): String {
        val millis = System.currentTimeMillis()

        val time = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("H:mm"))
        return time
    }

    private fun applyStyle(textView: TextView, string: String, input: String) {
        val text = string.plus(" $input")
        val spannable = SpannableString(text)
        val start = text.indexOf(string)
        if (start >= 0) {
            spannable.setSpan(
                TextAppearanceSpan(this, R.style.CustomTextStyleSemiBold14spGray),
                start,
                start + string.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannable
    }


    private fun generatePdfAndShare() {
        // 1. Get the view you want to convert (your meal card container)
        val pdfView = layoutInflater.inflate(R.layout.layout_pdf_meal, null)


        pdfView.findViewById<TextView>(R.id.mealName).text = defaultMeal.dishName
        pdfView.findViewById<TextView>(R.id.totalCalo).text =
            totalNutrition.calories.toInt().toString()
        pdfView.findViewById<TextView>(R.id.kcal).text = measuredUnit.calories


        val proteinProcess = totalNutrition.proteins * 100 / totalCalories
        pdfView.findViewById<TextView>(R.id.proteinPercent).text =
            round(proteinProcess * 100 / 100).toString().plus(" %")
        pdfView.findViewById<TextView>(R.id.proteinValue).text =
            totalNutrition.proteins.toString().plus(" ${measuredUnit.proteins}")
        pdfView.findViewById<ProgressBar>(R.id.proteinProgress).progress = proteinProcess.toInt()

        val carbProcess = totalNutrition.carbs * 100 / totalCalories
        pdfView.findViewById<TextView>(R.id.carbPercent).text =
            round(carbProcess * 100 / 100).toString().plus(" %")
        pdfView.findViewById<TextView>(R.id.carbValue).text =
            totalNutrition.carbs.toString().plus(" ${measuredUnit.carbs}")
        pdfView.findViewById<ProgressBar>(R.id.carbProgress).progress = carbProcess.toInt()

        val fatProcess = totalNutrition.fats * 100 / totalCalories
        pdfView.findViewById<TextView>(R.id.fatPercent).text =
            round(fatProcess * 100 / 100).toString().plus(" %")
        pdfView.findViewById<TextView>(R.id.fatValue).text =
            totalNutrition.fats.toString().plus(" ${measuredUnit.fats}")
        pdfView.findViewById<ProgressBar>(R.id.fatProgress).progress = fatProcess.toInt()

        applyStyle(
            pdfView.findViewById<TextView>(R.id.mealDetailName),
            resources.getString(R.string.meal_name),
            defaultMeal.dishName
        )
        applyStyle(
            pdfView.findViewById<TextView>(R.id.mealDetailIngredients),
            resources.getString(R.string.meal_ingredient),
            defaultMeal.ingredients.joinToString(", ") {
                it.foodName
            })

        pdfView.findViewById<RecyclerView>(R.id.allIngredients).adapter = ingredientAdapter
        applyStyle(
            pdfView.findViewById<TextView>(R.id.mealEnergy),
            resources.getString(R.string.meal_energy),
            totalNutrition.calories.toInt().toString().plus(" ${measuredUnit.calories}")
        )

        pdfView.findViewById<TextView>(R.id.carbonValue).text =
            totalNutrition.carbs.toString().plus(" ${measuredUnit.carbs}")
        pdfView.findViewById<TextView>(R.id.proteinValue).text =
            totalNutrition.proteins.toString().plus(" ${measuredUnit.proteins}")
        pdfView.findViewById<TextView>(R.id.fatTotalValue).text =
            totalNutrition.fats.toString().plus(" ${measuredUnit.fats}")
        pdfView.findViewById<TextView>(R.id.solidValue).text =
            totalNutrition.solids.toString().plus(" ${measuredUnit.solids}")
        pdfView.findViewById<TextView>(R.id.liquidValue).text =
            totalNutrition.liquids.toString().plus(" ${measuredUnit.liquids}")


        // 2. Measure and layout the view
        val displayMetrics = resources.displayMetrics
        pdfView.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        pdfView.layout(0, 0, pdfView.measuredWidth, pdfView.measuredHeight)

        // 3. Create PDF document
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pdfView.width, pdfView.height, 1).create()
        val page = document.startPage(pageInfo)

        // 4. Draw the view onto the PDF page
        val canvas = page.canvas
        pdfView.draw(canvas)
        document.finishPage(page)

        // 5. Save PDF to cache directory
        val pdfFile = File(this@DetailActivity.cacheDir, "${defaultMeal.dishName}.pdf")
        document.writeTo(FileOutputStream(pdfFile))
        document.close()

        // 6. Share the PDF using Intent
        val uri = FileProvider.getUriForFile(
            this,
            "${this.packageName}.fileprovider",
            pdfFile
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Meal PDF"))
    }

    private fun formatTimestamp(timestamp: Long): String {
        // Use proper pattern: HH:mm - dd/MM/yyyy (day/month/year)
        val formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy")
            .withZone(ZoneId.systemDefault()) // converts to your device's local timezone

        // Convert timestamp (milliseconds) to Instant, then format
        val formattedDate = formatter.format(Instant.ofEpochMilli(timestamp))

        println("formatTimestamp: $timestamp -> $formattedDate")
        return formattedDate
    }

}


class RotateTransformation(private val rotateRotationAngle: Float) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(("rotate$rotateRotationAngle").toByteArray(Charsets.UTF_8))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotateRotationAngle)
        return Bitmap.createBitmap(
            toTransform,
            0,
            0,
            toTransform.width,
            toTransform.height,
            matrix,
            true
        )
    }
}