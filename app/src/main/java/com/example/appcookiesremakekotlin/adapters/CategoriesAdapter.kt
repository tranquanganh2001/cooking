package com.example.appcookiesremakekotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.pojo.Category
import com.example.appcookiesremakekotlin.databinding.CategoryItemsBinding

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.MyHolder>() {

    private var categoryList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun setCategories(categoryList: List<Category>) {
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class MyHolder(var binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategorieName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


}