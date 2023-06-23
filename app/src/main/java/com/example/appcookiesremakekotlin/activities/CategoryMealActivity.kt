package com.example.appcookiesremakekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appcookiesremakekotlin.R
import com.example.appcookiesremakekotlin.adapters.CategoryMealsAdapter
import com.example.appcookiesremakekotlin.databinding.ActivityCategoryMealBinding
import com.example.appcookiesremakekotlin.fragments.HomeFragment
import com.example.appcookiesremakekotlin.viewmodel.CategoryMealViewModel

class CategoryMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var categoryMealMvvm: CategoryMealViewModel
    private lateinit var categoryMealAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealMvvm = ViewModelProviders.of(this)[CategoryMealViewModel::class.java]

        categoryMealMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealMvvm.observeMealsByCategory().observe(this
        ) { mealList ->
            binding.tvCategoryCount.text = mealList.size.toString()
            categoryMealAdapter.setMealsList(mealList)
        }

        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        categoryMealAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryMealAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }
    }


}