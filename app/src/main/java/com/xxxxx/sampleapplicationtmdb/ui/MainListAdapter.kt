package com.xxxxx.sampleapplicationtmdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.databinding.ItemListBinding

class MainListAdapter(val clickListener: ItemClickListener) : RecyclerView.Adapter<MainListAdapter.ItemViewHolder>() {

    private val items: ArrayList<MovieItem> = arrayListOf()

    fun setItems(newItems: List<MovieItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    inner class ItemViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }

}

class ItemClickListener(val clickListener: (item: MovieItem) -> Unit) {
    fun onClick(item: MovieItem) = clickListener(item)
}

