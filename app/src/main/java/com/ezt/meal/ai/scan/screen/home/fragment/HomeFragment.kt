package com.ezt.meal.ai.scan.screen.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.databinding.FragmentHomeBinding
import com.ezt.meal.ai.scan.screen.BaseFragment
import com.ezt.meal.ai.scan.screen.camera.CameraActivity
import com.ezt.meal.ai.scan.screen.search.SearchActivity
import com.ezt.meal.ai.scan.screen.home.adapter.RecentMealAdapter
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.utils.Common.visible
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val mealViewModel: MealViewModel by viewModels()
    private lateinit var recentMealAdapter: RecentMealAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            scanning.setOnClickListener {
                startActivity(Intent(requireContext(), CameraActivity::class.java))

            }

            scanBtn.setOnClickListener {
                startActivity(Intent(requireContext(), CameraActivity::class.java))
            }
            searchIcon.setOnClickListener {
                startActivity(Intent(requireContext(), SearchActivity::class.java))
            }
            searchText.setOnClickListener {
                startActivity(Intent(requireContext(), SearchActivity::class.java))
            }
            nutritionMeal.setOnClickListener {
                startActivity(Intent(requireContext(), SearchActivity::class.java))
            }

            seeMore.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(HomeFragmentDirections.actionHomeToRecentFragment())
            }

            recentMealAdapter = RecentMealAdapter()

            recentItems.adapter = recentMealAdapter
            recentItems.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)


            noRecentScan.gone()
            recentScan.gone()
            seeMore.gone()
            recentItems.gone()


            mealViewModel.recentMeals.observe(viewLifecycleOwner) { data ->
                if (data.isEmpty()) {
                    noRecentScan.visible()
                    recentScan.gone()
                    seeMore.gone()
                    recentItems.gone()


                } else {
                    noRecentScan.gone()
                    recentScan.visible()
                    seeMore.visible()
                    recentItems.visible()

                    recentMealAdapter.submitList(data)

                }
            }


        }
    }
}

