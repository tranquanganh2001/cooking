package com.example.appcookiesremakekotlin.retrofit

import com.example.appcookieskotlin.pojo.CategoryList
import com.example.appcookieskotlin.pojo.MealList
import com.example.appcookieskotlin.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName:String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String): Call<MealsByCategoryList>

    @GET("filter.php?")
    fun getExploreItem(@Query("c") categoryName:String): Call<MealsByCategoryList>

}