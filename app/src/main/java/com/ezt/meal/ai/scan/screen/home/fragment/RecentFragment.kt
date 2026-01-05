package com.ezt.meal.ai.scan.screen.home.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezt.meal.ai.scan.R
import com.ezt.meal.ai.scan.databinding.FragmentRecentBinding
import com.ezt.meal.ai.scan.screen.BaseFragment
import com.ezt.meal.ai.scan.screen.home.adapter.RecentDate
import com.ezt.meal.ai.scan.screen.home.adapter.RecentDateAdapter
import com.ezt.meal.ai.scan.screen.home.adapter.RecentMealAdapter
import com.ezt.meal.ai.scan.utils.Common.gone
import com.ezt.meal.ai.scan.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate) {
    private var startDate = 0L
    private var endDate = 0L

    private val mealViewModel: MealViewModel by viewModels()
    private lateinit var recentMealAdapter: RecentMealAdapter
    private val  recentDateAdapter: RecentDateAdapter by lazy {
        RecentDateAdapter { recentDate ->
            if(recentDate != RecentDate.RECENT_DATE_DEFAULT) {
                val result = getDayRangeMillis(recentDate)
                mealViewModel.getMealBetweenDates(result.first, result.second)
            } else {
                mealViewModel.getMeals()
            }
        }
    }

    fun getDayRangeMillis(date: RecentDate): Pair<Long, Long> {
        // Convert RecentDate to LocalDate (using current year)
        val monthNumber = java.time.Month.valueOf(date.month.uppercase()).value
        val localDate = LocalDate.of(LocalDate.now().year, monthNumber, date.day)

        // Start of day: 00:00
        val fromValue = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // End of day: 23:59
        val toValue = localDate.atTime(23, 59)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return Pair(fromValue, toValue)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentMealAdapter = RecentMealAdapter(false)

        mealViewModel.getMeals()

        binding.apply {
            updateSearchList()
            allItems.adapter = recentMealAdapter
            resetButton.gone()
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

            resetButton.setOnClickListener {
                startDate = 0
                endDate = 0
                selectedStartDate.text = resources.getString(R.string.start_date)
                selectedEndDate.text = resources.getString(R.string.end_date)
                mealViewModel.getMeals()
            }

            mealViewModel.selectedMeals.observe(viewLifecycleOwner) { data ->
                recentMealAdapter.submitList(data)
            }
        }
    }

    private fun updateSearchList() {
        val today = LocalDate.now()

        // Current month info
        val currentYearMonth = YearMonth.from(today)
        val currentMonthName = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val daysInCurrentMonth = currentYearMonth.lengthOfMonth()

        // Previous month info
        val previousYearMonth = currentYearMonth.minusMonths(1)
        val previousMonthName = previousYearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val daysInPreviousMonth = previousYearMonth.lengthOfMonth()

        // Create list: last 3 days of previous month + current month
        val previousMonthDays = (daysInPreviousMonth - 2..daysInPreviousMonth).map { day ->
            RecentDate(month = previousMonthName, day = day)
        }

        val currentMonthDays = (1..daysInCurrentMonth).map { day ->
            RecentDate(month = currentMonthName, day = day)
        }

        val allDates = previousMonthDays + currentMonthDays

        binding.allDates.adapter = recentDateAdapter
        binding.allDates.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recentDateAdapter.submitList(allDates)
    }

}