package com.ezt.meal.ai.scan.screen.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.DisplayContext
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.ActivityCameraBinding
import com.ezt.meal.ai.scan.model.Ingredient
import com.ezt.meal.ai.scan.model.MeasurementUnits
import com.ezt.meal.ai.scan.model.NutritionResponse
import com.ezt.meal.ai.scan.model.TotalNutrition
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.camera.dialog.BackDialog
import com.ezt.meal.ai.scan.screen.camera.dialog.DetectFailedDialog
import com.ezt.meal.ai.scan.screen.camera.dialog.GuidanceDialog
import com.ezt.meal.ai.scan.screen.detail.DetailActivity
import com.ezt.meal.ai.scan.utils.Common
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.utils.Common.visible
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.scale
import com.ezt.meal.ai.scan.model.ApiResponse
import com.ezt.meal.ai.scan.screen.camera.customview.SquareHoleOverlayView
import com.ezt.meal.ai.scan.screen.camera.customview.dp
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CameraActivity :
    BaseActivity<ActivityCameraBinding>(ActivityCameraBinding::inflate) {

    private val mealViewModel: MealViewModel by viewModels()
    private var imagePath = ""

    private var lensFacing = CameraSelector.LENS_FACING_BACK

    private lateinit var imageCapture: ImageCapture

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // 1️⃣ Convert Uri to a temp JPEG file < 4MB
            val tempFile = createJpegFileFromUri(uri, this)
            mealViewModel.detectMealFromImage(tempFile)
            binding.analyzingLayout.visible().also {
                binding.foodAnalyze.visible()
                binding.foodProgress.visible()
                binding.foodIcon.gone()

                binding.volumeAnalyze.visible()
                binding.volumeProgress.visible()
                binding.volumeIcon.gone()

                binding.caloryAnalyze.visible()
                binding.caloryProgress.visible()
                binding.caloryIcon.gone()


            }
            binding.bottomController.gone()
            binding.backBtn.gone()
            binding.guideBtn.gone()
        }
    }

    fun createJpegFileFromUri(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open input stream from URI")

        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        var quality = 100
        var outputBytes: ByteArray
        do {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            outputBytes = baos.toByteArray()
            quality -= 5
        } while (outputBytes.size > 4 * 1024 * 1024 && quality > 10) // <4MB

        // Save to temp file
        val tempFile = File(context.cacheDir, "selected_image.jpg")
        FileOutputStream(tempFile).use { fos ->
            fos.write(outputBytes)
        }

        return tempFile
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.insetsController?.hide(
            WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
        )

        if (hasCameraPermission(this@CameraActivity)) {
            startCamera()
        } else {
            requestCameraPermission()
        }

        binding.apply {
            overlayView.post {
                    val holeBottom =   overlayView.getHoleRect().bottom.toInt()
                    val params = analyzingLayout.layoutParams as ConstraintLayout.LayoutParams
                params.topMargin = holeBottom + 10.dp.toInt()
                analyzingLayout.layoutParams = params
            }


            backBtn.setOnClickListener {
                val dialog = BackDialog(this@CameraActivity, {
                    finish()
                })
                    dialog.show()
            }

            takePhoto.setOnClickListener {
                if (hasCameraPermission(this@CameraActivity)) {
                    takePhoto()
                } else {
                    showGoToSettingsDialog()
                }

//                startActivity(Intent(this@CameraActivity, DetailActivity::class.java).apply {
//                    putExtra("defaultMeal", Gson().toJson(DEFAULT_MEAL))
//                })
            }

            reverseCam.setOnClickListener {
                return@setOnClickListener
                lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                    CameraSelector.LENS_FACING_FRONT
                } else {
                    CameraSelector.LENS_FACING_BACK
                }

                // Restart camera with the new lens
                startCamera()
            }

            gallery.setOnClickListener {
                return@setOnClickListener
                pickImageLauncher.launch("image/*") // Only images
            }

            guideBtn.setOnClickListener {
                val dialog = GuidanceDialog(this@CameraActivity)
                dialog.show()
            }

            mealViewModel.detectedMeal.observe(this@CameraActivity) { result ->
                println("detectedMeal: $result")


                binding.bottomController.visible()
                binding.backBtn.visible()
                binding.guideBtn.visible()

                if(result.data.data.ingredients.isNullOrEmpty() || result.data.queue.input.isNullOrEmpty() || result == ApiResponse.DEFAULT_RESPONSE) {
                    binding.analyzingLayout.gone()
                    val dialog = DetectFailedDialog(this@CameraActivity)
                    dialog.show()
                } else {
                    binding.caloryProgress.gone()
                    binding.caloryIcon.visible()

                    binding.volumeProgress.gone()
                    binding.volumeIcon.visible()

                    binding.foodProgress.gone()
                    binding.foodIcon.visible()

                    Handler(Looper.getMainLooper()).postDelayed({
                        analyzingLayout.gone()
                        startActivity(Intent(this@CameraActivity, DetailActivity::class.java).apply {
                            val defaultMeal = Gson().toJson(result.data.data)
                            putExtra("defaultMeal", defaultMeal)
                            putExtra("imagePath", result.data.queue.input.first().url)
                            putExtra("isDetected", true)
                        })
                    }, 500L)
                }

            }
        }
    }

    private fun showGoToSettingsDialog() {
        Common.showDialogGoToSetting(this) { result ->
            if (result) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            100
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetRotation(binding.previewView.display.rotation)
                .build()
                .apply {
                    setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val radiusPx = 12.dp
            binding.previewView.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, radiusPx)
                }
            }
            binding.previewView.clipToOutline = true

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetRotation(binding.previewView.display.rotation)
                .build()

            // Dynamically choose camera based on lensFacing
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.e("CameraX", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File.createTempFile(
            "camera_",
            ".jpg",
            cacheDir // TEMP storage
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // 1. Load bitmap from saved file
                    val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

                    // 2. Resize bitmap if too large (optional)
                    val maxWidth = 1080
                    val maxHeight = 1080
                    val ratio = minOf(maxWidth.toFloat() / bitmap.width, maxHeight.toFloat() / bitmap.height, 1f)
                    val resizedBitmap = bitmap.scale(
                        (bitmap.width * ratio).toInt(),
                        (bitmap.height * ratio).toInt()
                    )

                    // 3. Compress bitmap to <= 2MB
                    val compressedFile = File.createTempFile("camera_compressed_", ".jpg", cacheDir)
                    var quality = 100
                    do {
                        FileOutputStream(compressedFile).use { fos ->
                            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
                        }
                        quality -= 5
                    } while (compressedFile.length() > 4 * 1024 * 1024 && quality > 0)

                    // 4. Use the compressed file
                    val savedUri = Uri.fromFile(compressedFile)
                    Log.d("CameraX", "Photo saved (compressed): $savedUri")

                    imagePath = compressedFile.absolutePath
                    mealViewModel.detectMealFromImage(compressedFile)
                    binding.analyzingLayout.visible().also {
                        binding.foodAnalyze.visible()
                        binding.foodProgress.visible()
                        binding.foodIcon.gone()

                        binding.volumeAnalyze.visible()
                        binding.volumeProgress.visible()
                        binding.volumeIcon.gone()

                        binding.caloryAnalyze.visible()
                        binding.caloryProgress.visible()
                        binding.caloryIcon.gone()


                    }
                    binding.bottomController.gone()
                    binding.backBtn.gone()
                    binding.guideBtn.gone()


                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Photo capture failed", exception)
                }
            }
        )
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startCamera()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val overlay = findViewById<SquareHoleOverlayView>(R.id.overlayView)
            val preview = findViewById<PreviewView>(R.id.previewView)

            val hole = overlay.getHoleRect()

            val params = preview.layoutParams as ConstraintLayout.LayoutParams
            params.width = hole.width().toInt()
            params.height = hole.height().toInt()
            params.topMargin = hole.top.toInt()
            params.leftMargin = hole.left.toInt()
            preview.layoutParams = params
        }
    }

    companion object {
        val DEFAULT_INGREDIENTS = listOf(
            Ingredient(foodName = "Burger Bun", estimatedAmount = 100.0, unit = "g", carbs = 20.0, fats = 3.0, proteins = 4.0, calories = 100.0),
            Ingredient(foodName = "Ground Beef Patty", estimatedAmount = 100.0, unit = "g", carbs = 0.0, fats = 20.0, proteins = 25.0, calories = 250.0),
            Ingredient(foodName = "Cheese", estimatedAmount = 20.0, unit = "g", carbs = 0.0, fats = 15.0, proteins = 5.0, calories = 120.0),
            Ingredient(foodName = "Lettuce", estimatedAmount = 30.0, unit = "g", carbs = 20.0, fats = 3.0, proteins = 4.0, calories = 100.0),
            Ingredient(foodName = "Tomato", estimatedAmount = 30.0, unit = "g", carbs = 3.0, fats = 0.2, proteins = 0.5, calories = 10.0),
            Ingredient(foodName = "French Fries", estimatedAmount = 100.0, unit = "g", carbs = 40.0, fats = 15.0, proteins = 3.0, calories = 300.0),
        )

        val DEFAULT_MEAL = NutritionResponse(
            dishName = "Hamburger with Fries",
            ingredients = DEFAULT_INGREDIENTS,
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 65.0, fats = 53.7, proteins = 38.0, calories = 795.0),
            processingTime = kotlin.math.round(5.60764217376709 * 100) / 100,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = 3,
            status = "success",
            message = "Food nutrition analyzed successfully"

        )
        val DEFAULT_MEAL_2 = NutritionResponse(
            dishName = "No Internet Connection",
            ingredients = DEFAULT_INGREDIENTS,
            totalNutrition = TotalNutrition(solids = 380.0, liquids = 0.0, estimatedAmount = "380.0 g", carbs = 65.0, fats = 53.7, proteins = 38.0, calories = 795.0),
            processingTime = kotlin.math.round(5.60764217376709 * 100) / 100,
            measurementUnits = MeasurementUnits(solids = "g", liquids = "ml", carbs = "g", fats = "g", proteins = "g", calories = "kcal"),
            healthyScore = 3,
            status = "success",
            message = "Food nutrition analyzed successfully"

        )

        fun hasCameraPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
