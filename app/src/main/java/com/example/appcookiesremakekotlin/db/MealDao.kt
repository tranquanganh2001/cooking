package com.example.appcookiesremakekotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appcookieskotlin.pojo.Meal

@Dao
interface MealDao {

    //một function có khả năng được started, pause và resume (và quá trình pause và resume
    // có thể được thực thi lại nhiều lần) và sau đó kết thúc.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>

}