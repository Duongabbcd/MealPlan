package com.ezt.meal.ai.scan.screen.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.ezt.meal.ai.scan.screen.detail.customview.MacroCircleView
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
        intent.getStringExtra("imageDrawable") ?: ""
    }
    private val mealDate by lazy {
        intent.getLongExtra("mealDate", -1L)
    }
    private val rotation by lazy {
        intent.getIntExtra("rotation", 0)
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
            println("rotation: $rotation")

            backBtn.setOnClickListener {
                finish()
            }

            if (imagePath.isNotEmpty()) {
                Glide.with(this@DetailActivity).load(imagePath).transform(RotateTransformation(
                    when(rotation) {
                        1 -> 90F
                        2 -> 270F
                        else -> 0F
                    }
                ))
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

            if(defaultMeal.ingredients.isEmpty()) {
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
                        date = System.currentTimeMillis(),
                        isFront = rotation
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

        // Ensure AppBar is fully expanded for PDF
        binding.appBarLayout.setExpanded(true, false)

        val appBar = binding.appBarLayout
        val scroll = binding.nestedScrollView

        // 1. Measure both views without detaching
        val width = resources.displayMetrics.widthPixels

        appBar.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        appBar.layout(0, 0, appBar.measuredWidth, appBar.measuredHeight)

        scroll.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        scroll.layout(0, 0, scroll.measuredWidth, scroll.measuredHeight)

        val totalHeight = appBar.measuredHeight + scroll.measuredHeight

        // 2. PDF pagination
        val pageHeight = 1600
        val document = PdfDocument()
        var pageNumber = 1
        var yOffset = 0

        while (yOffset < totalHeight) {

            val pageInfo = PdfDocument.PageInfo.Builder(
                width,
                pageHeight,
                pageNumber++
            ).create()

            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            canvas.save()

            // Draw AppBar
            canvas.translate(0f, -yOffset.toFloat())
            appBar.draw(canvas)

            // Draw scroll content BELOW AppBar
            canvas.translate(0f, appBar.measuredHeight.toFloat())
            scroll.draw(canvas)

            canvas.restore()

            document.finishPage(page)
            yOffset += pageHeight
        }

        // 3. Save
        val pdfFile = File(cacheDir, "${defaultMeal.dishName}.pdf")
        document.writeTo(FileOutputStream(pdfFile))
        document.close()

        // 4. Share
        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            pdfFile
        )

        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                },
                "Share Meal PDF"
            )
        )
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