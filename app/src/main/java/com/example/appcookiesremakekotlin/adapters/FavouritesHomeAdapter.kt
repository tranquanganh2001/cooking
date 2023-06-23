package com.example.appcookiesremakekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.pojo.Meal
import com.example.appcookieskotlin.pojo.MealsByCategory
import com.example.appcookiesremakekotlin.databinding.ExploreItemsBinding
import com.example.appcookiesremakekotlin.databinding.MealItemsBinding

class FavouritesHomeAdapter: RecyclerView.Adapter<FavouritesHomeAdapter.MyHolder>() {

    inner class MyHolder(val binding: ExploreItemsBinding): RecyclerView.ViewHolder(binding.root)
    lateinit var onItemClick:((Meal) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ExploreItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgItems)
        //holder.binding.txtName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}