package com.example.appcookiesremakekotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.pojo.Meal
import com.example.appcookieskotlin.pojo.MealsByCategory
import com.example.appcookiesremakekotlin.activities.CategoryMealActivity
import com.example.appcookiesremakekotlin.activities.HomeActivity
import com.example.appcookiesremakekotlin.activities.MealActivity
import com.example.appcookiesremakekotlin.adapters.CategoriesAdapter
import com.example.appcookiesremakekotlin.adapters.FavouritesHomeAdapter
import com.example.appcookiesremakekotlin.adapters.FavouritesMealsAdapter
import com.example.appcookiesremakekotlin.adapters.MostPopularAdapter
import com.example.appcookiesremakekotlin.databinding.FragmentHomeBinding
import com.example.appcookiesremakekotlin.fragments.bottomsheet.MealBottomSheetFragment
import com.example.appcookiesremakekotlin.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var adapterFavoritesHome: FavouritesHomeAdapter

    companion object {
        const val MEAL_ID = "com.example.appcookiesremakekotlin.fragments.idMeal"
        const val MEAL_NAME = "com.example.appcookiesremakekotlin.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.appcookiesremakekotlin.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.appcookiesremakekotlin.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
        popularAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observepopularItems()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoryItems()
        onCategoryClick()

        prepareRecyclerViewHome()
        observeFavouritesHome()
        onFavouritesHomeClick()

        onPopularItemLongClick()

    }

    private fun onPopularItemLongClick() {
        popularAdapter.onLongItemClick = {meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoryItems() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) { categories ->
            categoriesAdapter.setCategories(categories)
        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recyclerViewOver.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observepopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    private fun onFavouritesHomeClick() {
        adapterFavoritesHome.onItemClick = { meal ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeFavouritesHome() {
        viewModel.observeFavouritesMealsLiveData().observe(requireActivity()
        ) { meals ->
            adapterFavoritesHome.differ.submitList(meals)
        }
    }

    private fun prepareRecyclerViewHome() {
        adapterFavoritesHome = FavouritesHomeAdapter()
        binding.recyclerExplore.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterFavoritesHome
        }
    }
}