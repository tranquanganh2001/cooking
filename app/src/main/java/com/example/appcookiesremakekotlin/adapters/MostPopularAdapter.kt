package com.example.appcookiesremakekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.pojo.Category
import com.example.appcookieskotlin.pojo.MealsByCategory
import com.example.appcookiesremakekotlin.databinding.PopularItemsBinding

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.MyHolder>() {

    lateinit var onItemClick:((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()
    var onLongItemClick:((MealsByCategory) -> Unit)? = null

    fun setMeals(mealsByCategoryList :ArrayList<MealsByCategory>) {
        this.mealsList = mealsByCategoryList
        notifyDataSetChanged()
    }

    class MyHolder(var binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularItems)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealsList[position])
            true
        }

    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

}