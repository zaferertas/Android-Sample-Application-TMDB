package com.xxxxx.sampleapplicationtmdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xxxxx.sampleapplicationtmdb.API_IMAGE_URL
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.databinding.ListItemBinding

class MovieListAdapter(val clickListener: ItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    private val items: ArrayList<MovieItem> = arrayListOf()

    fun setItems(newItems: List<MovieItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    inner class ItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem) {
            binding.title.text = item.title
            binding.releaseDate.text = item.releaseDate
            if (item.posterPath != null) {
                Glide.with(itemView.context).load(API_IMAGE_URL + item.posterPath)
                    .into(binding.image)
            }
            binding.root.setOnClickListener {
                clickListener.onClick(item)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(item: MovieItem)
    }

}


