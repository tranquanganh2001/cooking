package com.example.appcookiesremakekotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appcookiesremakekotlin.R
import com.example.appcookiesremakekotlin.activities.HomeActivity
import com.example.appcookiesremakekotlin.activities.MealActivity
import com.example.appcookiesremakekotlin.adapters.CategoryMealsAdapter
import com.example.appcookiesremakekotlin.adapters.FavouritesMealsAdapter
import com.example.appcookiesremakekotlin.databinding.FragmentFavouritesBinding
import com.example.appcookiesremakekotlin.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesAdapter: FavouritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeFavourites()

        onFavouritesClick()

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val positon = viewHolder.adapterPosition
                val deletedMeal = favouritesAdapter.differ.currentList[positon]
                viewModel.deleteMeal(deletedMeal)

                Snackbar.make(requireView(), "Meal Delete", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deletedMeal)
                    }
                ).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun onFavouritesClick() {
        favouritesAdapter.onItemClick = { meal ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeFavourites() {
        viewModel.observeFavouritesMealsLiveData().observe(requireActivity()
        ) { meals ->
            favouritesAdapter.differ.submitList(meals)
        }
    }

    private fun prepareRecyclerView() {
        favouritesAdapter = FavouritesMealsAdapter()
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favouritesAdapter
        }
    }
}