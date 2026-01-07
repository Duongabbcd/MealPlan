package com.ezt.meal.ai.scan.screen.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.databinding.ActivitySearchBinding
import com.ezt.meal.ai.scan.screen.base.BaseActivity
import com.ezt.meal.ai.scan.screen.search.adapter.SearchMealAdapter
import com.ezt.meal.ai.scan.utils.Utils.hideKeyBoard
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    private lateinit var searchMealAdapter: SearchMealAdapter

    private var searchMeal = ""

    private val model: MealViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.getMealsByName()
        searchMealAdapter = SearchMealAdapter()
        binding.apply {

            allSearch.adapter = searchMealAdapter

            backBtn.setOnClickListener {
                finish()
            }

            model.searchMeals.observe(this@SearchActivity) { list ->
                list.onEach {
                    println("searchMeals: $it")
                }
                searchMealAdapter.submitList(list)
            }

            allSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        this@SearchActivity.hideKeyBoard(binding.searchText)
                    }

                }
            })



            searchText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 🔥 This is called every time the user types or deletes a character
                    val result = s.toString()
                    searchMeal = result
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            searchIcon.setOnClickListener {
                model.getMealsByName(searchMeal.trim())
            }

        }
    }
}