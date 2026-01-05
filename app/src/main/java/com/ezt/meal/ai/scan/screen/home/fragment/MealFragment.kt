package com.ezt.meal.ai.scan.screen.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ezt.meal.ai.scan.databinding.FragmentHomeBinding
import com.ezt.meal.ai.scan.databinding.FragmentMealBinding
import com.ezt.meal.ai.scan.screen.BaseFragment
import com.ezt.meal.ai.scan.screen.camera.CameraActivity

class MealFragment: BaseFragment<FragmentMealBinding>(FragmentMealBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            scanBtn.setOnClickListener {
                startActivity(Intent(requireContext(), CameraActivity::class.java))
            }
        }
    }
}