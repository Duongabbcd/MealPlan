package com.ezt.meal.ai.scan.screen.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.ActivityMainBinding
import com.ezt.meal.ai.scan.databinding.DialogExitAppBinding
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.camera.CameraActivity
import com.ezt.meal.ai.scan.screen.search.SearchActivity
import com.ezt.meal.ai.scan.utils.Common
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val mealViewModel: MealViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val countOpen = Common.getCountOpenApp(this)
        if (countOpen == 0) {
            Common.setCountOpenApp(this, 1)
        }
        binding.apply {
            mealViewModel.deleteMeal()
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.frameLayout) as NavHostFragment
            navController = navHostFragment.findNavController()

            updateButtonColor(home, listOf(search, recent, settings))
            updateImageColor(0)

            home.setOnClickListener {
                updateButtonColor(home, listOf(search, recent, settings))
                updateImageColor(0)
                navController.navigate(R.id.homeFragment)
            }

            search.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }

            recent.setOnClickListener {
                updateButtonColor(recent, listOf(search, home, settings))
                updateImageColor(2)
                navController.navigate(R.id.recentFragment)
            }

            settings.setOnClickListener {
                updateButtonColor(settings, listOf(search, recent, home))
                updateImageColor(3)
                navController.navigate(R.id.settingsFragment)
            }

            fabScanner.setOnClickListener {
                startActivity(Intent(this@MainActivity, CameraActivity::class.java))
            }
        }
    }

    private fun updateImageColor(
        selectedIndex: Int = 0
    ) {
        val selected = when (selectedIndex) {
            0 -> binding.home
            1 -> binding.search
            2 -> binding.recent
            else -> binding.settings
        }
        // 1️⃣ Update selected TextView
        val newResId = when (selectedIndex) {
            0 -> R.drawable.icon_home_green
            1 -> R.drawable.icon_search_green
            2 -> R.drawable.icon_recent_green
            else -> R.drawable.icon_setting_green
        }
        val selectedDrawable = ContextCompat.getDrawable(this, newResId)
        selectedDrawable?.setBounds(
            0,
            0,
            selectedDrawable.intrinsicWidth,
            selectedDrawable.intrinsicHeight
        )
        val selectedDrawables = selected.compoundDrawables
        selected.setCompoundDrawables(
            selectedDrawables[0],
            selectedDrawable,
            selectedDrawables[2],
            selectedDrawables[3]
        )

        val unselected = when (selectedIndex) {
            0 -> listOf(binding.search, binding.recent, binding.settings)
            1 -> listOf(binding.home, binding.recent, binding.settings)
            2 -> listOf(binding.search, binding.home, binding.settings)
            else -> listOf(binding.search, binding.recent, binding.home)
        }
        // 2️⃣ Update unselected TextViews
        unselected.onEach { textView ->
            // Map index to the corresponding icon, e.g., icon_new_1, icon_new_2, ...
            val resId = when (textView) {
                binding.home -> R.drawable.icon_home
                binding.search -> R.drawable.icon_search_gray
                binding.recent -> R.drawable.icon_recent
                else -> R.drawable.icon_setting
            }

            val topDrawable = ContextCompat.getDrawable(this, resId)
            topDrawable?.setBounds(0, 0, topDrawable.intrinsicWidth, topDrawable.intrinsicHeight)
            val drawables = textView.compoundDrawables
            textView.setCompoundDrawables(
                drawables[0],
                topDrawable,
                drawables[2],
                drawables[3]
            )
        }
    }


    private fun updateButtonColor(
        selected: TextView,
        unselected: List<TextView>
    ) {
        selected.setTextColor(resources.getColor(R.color.secondColor))
        unselected.onEach {
            it.setTextColor(resources.getColor(R.color.unselected))
        }
    }
}


class ExitAppDialog(
    context: Context,
    private val onClickListener: () -> Unit
) : Dialog(context) {
    private val binding by lazy { DialogExitAppBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(R.color.transparent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            textCancel.setOnClickListener {
                dismiss()
            }

            textExit.setOnClickListener {
                onClickListener()
                dismiss()
            }
        }
    }
}