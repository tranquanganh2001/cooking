package com.example.appcookiesremakekotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appcookiesremakekotlin.R
import com.example.appcookiesremakekotlin.activities.CategoryMealActivity
import com.example.appcookiesremakekotlin.activities.HomeActivity
import com.example.appcookiesremakekotlin.adapters.CategoriesAdapter
import com.example.appcookiesremakekotlin.databinding.FragmentCategoriesBinding
import com.example.appcookiesremakekotlin.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoriesRecyclerView()
        observerCategoryItems()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun observerCategoryItems() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) { categories ->
            categoriesAdapter.setCategories(categories)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}