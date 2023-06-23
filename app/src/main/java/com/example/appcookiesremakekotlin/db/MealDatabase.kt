package com.example.appcookiesremakekotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appcookieskotlin.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverters::class)
abstract class MealDatabase: RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        //Đánh dấu một trường có thể thay đổi giá trị của nó nằm
        // ngoài sự kiểm soát của chương trình
        var INSTANCE: MealDatabase? = null

        @Synchronized //đảm bảo rằng chỉ một luồng có thể thực thi mã
        fun getInstance(context: Context):MealDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration() //muốn xây lại csdl nhưng vẫn giữ dl bên trong csdl đó
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }



}