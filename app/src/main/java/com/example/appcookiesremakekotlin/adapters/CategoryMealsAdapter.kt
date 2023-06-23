package com.example.appcookiesremakekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.pojo.MealsByCategory
import com.example.appcookiesremakekotlin.databinding.MealItemsBinding

class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.MyHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory) -> Unit)

    fun setMealsList(mealList: List<MealsByCategory>) {
        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class MyHolder(var binding: MealItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MealItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}