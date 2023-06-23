package com.example.appcookiesremakekotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcookieskotlin.pojo.MealsByCategory
import com.example.appcookieskotlin.pojo.MealsByCategoryList
import com.example.appcookiesremakekotlin.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel(): ViewModel() {

    var mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {  mealList ->
                    mealsLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("CategoryMealsActivity", t.message.toString())
            }
        })
    }

    fun observeMealsByCategory(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }

}