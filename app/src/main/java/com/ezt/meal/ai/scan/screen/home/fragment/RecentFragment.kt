package com.ezt.meal.ai.scan.screen.home.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.FragmentRecentBinding
import com.ezt.meal.ai.scan.screen.BaseFragment
import com.ezt.meal.ai.scan.screen.camera.CameraActivity
import com.ezt.meal.ai.scan.screen.home.adapter.RecentDate
import com.ezt.meal.ai.scan.screen.home.adapter.RecentDateAdapter
import com.ezt.meal.ai.scan.screen.home.adapter.RecentMealAdapter
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.utils.Common.visible
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate) {
    private var startDate = 0L
    private var endDate = 0L

    private val mealViewModel: MealViewModel by viewModels()
    private lateinit var recentMealAdapter: RecentMealAdapter

    private var didAutoScroll = false

    private val  recentDateAdapter: RecentDateAdapter by lazy {
        RecentDateAdapter { recentDate ->
            if(recentDate != RecentDate.RECENT_DATE_DEFAULT) {
                val result = getDayRangeMillis(recentDate)
                println("recentDateAdapter: ${result.first} and ${result.second}")
                mealViewModel.getMealBetweenDates(result.first, result.second)
            }
        }
    }

    fun getDayRangeMillis(date: RecentDate): Pair<Long, Long> {
        val localDate = LocalDate.of(date.year, date.month, date.day)

        val fromValue = localDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli()
        val toValue = localDate.atTime(23, 59).atZone(ZoneId.systemDefault())
            .toInstant().toEpochMilli()

        return Pair(fromValue, toValue)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentMealAdapter = RecentMealAdapter(false)

        binding.apply {
            allItems.adapter = recentMealAdapter
            resetButton.gone()
            setupDate()

            resetButton.setOnClickListener {
                startDate = 0
                endDate = 0
                selectedStartDate.text = resources.getString(R.string.start_date)
                selectedEndDate.text = resources.getString(R.string.end_date)
                mealViewModel.getMeals()
            }

            scanBtn.setOnClickListener {
                withSafeContext { ctx ->
                    didAutoScroll = false
                    startActivity(Intent(ctx, CameraActivity::class.java))
                }
            }

            mealViewModel.selectedMeals.observe(viewLifecycleOwner) { data ->

                if(data.isEmpty()) {
                    noRecentScan.visible()
                    allItems.gone()
                } else {
                    noRecentScan.gone()
                    allItems.visible()
                    recentMealAdapter.submitList(data)
                }

            }
        }
    }

    private fun setupDate() {
      binding.apply {
          fromValue.setOnClickListener {
              val calendar = Calendar.getInstance()

              DatePickerDialog(
                  requireContext(),
                  { _, year, month, dayOfMonth ->

                      calendar.set(year, month, dayOfMonth, 0, 0, 0)
                      calendar.set(Calendar.MILLISECOND, 0)

                      startDate = calendar.timeInMillis
                      selectedStartDate.text = startDate.toString()

                      Toast.makeText(
                          requireContext(), resources.getString(R.string.end_date),
                          Toast.LENGTH_SHORT
                      ).show()

                      fromValue.text =
                          SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                              .format(calendar.time)

                  },
                  calendar.get(Calendar.YEAR),
                  calendar.get(Calendar.MONTH),
                  calendar.get(Calendar.DAY_OF_MONTH)
              ).show()
          }

          toValue.setOnClickListener {
              val calendar = Calendar.getInstance()

              DatePickerDialog(
                  requireContext(),
                  { _, year, month, dayOfMonth ->

                      calendar.set(year, month, dayOfMonth, 0, 0, 0)
                      calendar.set(Calendar.MILLISECOND, 0)

                      endDate = calendar.timeInMillis

                      if (endDate <= startDate || startDate == 0L) {
                          Toast.makeText(
                              requireContext(),
                              resources.getString((R.string.select_date_warning)),
                              Toast.LENGTH_SHORT
                          ).show()
                          return@DatePickerDialog
                      }


                      selectedEndDate.text = endDate.toString()
                      mealViewModel.getMealBetweenDates(startDate, endDate)

                      toValue.text =
                          SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                              .format(calendar.time)

                  },
                  calendar.get(Calendar.YEAR),
                  calendar.get(Calendar.MONTH),
                  calendar.get(Calendar.DAY_OF_MONTH)
              ).show()
          }
      }
    }

    private fun updateSearchList() {
        val today = LocalDate.now()

        val currentYearMonth = YearMonth.from(today)
        val previousYearMonth = currentYearMonth.minusMonths(1)

        val daysInCurrentMonth = currentYearMonth.lengthOfMonth()
        val daysInPreviousMonth = previousYearMonth.lengthOfMonth()

        // Include year in RecentDate
        val previousMonthDays = (1..daysInPreviousMonth).map { day ->
            RecentDate(previousYearMonth.year, previousYearMonth.monthValue, day)
        }

        val currentMonthDays = (1..daysInCurrentMonth).map { day ->
            RecentDate(currentYearMonth.year, currentYearMonth.monthValue, day)
        }

        val allRecentDates = previousMonthDays + currentMonthDays

        binding.allDates.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.allDates.adapter = recentDateAdapter

        val todayIndex = allRecentDates.indexOfFirst {
            it.day == today.dayOfMonth && it.month == today.monthValue
        }

        recentDateAdapter.submitList(allRecentDates) {
            println("recentDateAdapter: $todayIndex")
            if (todayIndex != -1) {
                recentDateAdapter.setSelectedPosition(todayIndex)
                autoScrollToToday(todayIndex)

                // ✅ Fetch meals for today automatically
                val todayDate = allRecentDates[todayIndex]
                val result = getDayRangeMillis(todayDate)
                mealViewModel.getMealBetweenDates(result.first, result.second)
            }
        }
    }


    private fun autoScrollToToday(
        index: Int
    ) {
        if (didAutoScroll) return

        binding.allDates.post {
            val layoutManager =
                binding.allDates.layoutManager as LinearLayoutManager

            layoutManager.scrollToPositionWithOffset(
                index,
                binding.allDates.width / 2
            )

            didAutoScroll = true
        }
    }

    override fun onResume() {
        super.onResume()
        updateSearchList()
    }
}