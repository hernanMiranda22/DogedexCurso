package com.example.dogedex.doglist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.dogedex.R
import com.example.dogedex.model.Dog
import com.example.dogedex.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private var onItemClickListener : ((Dog) -> Unit)? = null

    fun setItemClickListener(onItemClickListener : (Dog) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener : ((Dog) -> Unit)? = null

    fun setLongItemClickListener(onLongItemClickListener : (Dog) -> Unit) {
        this.onLongItemClickListener = onLongItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))

        return DogViewHolder(binding)

    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)

        dogViewHolder.bind(dog)
    }

    inner class DogViewHolder(private val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dog: Dog) {

            if (dog.inCollection) {

                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_background
                )
                binding.dogImage.visibility = View.VISIBLE
                binding.dogIndex.visibility = View.GONE
                binding.dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }
                binding.dogImage.load(dog.imageUrl)

            }else{
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_null_background
                )
                binding.dogImage.visibility = View.GONE
                binding.dogIndex.visibility = View.VISIBLE
                binding.dogIndex.text = dog.index.toString()
                binding.dogListItemLayout.setOnLongClickListener {
                    onLongItemClickListener?.invoke(dog)
                    true
                }
            }
        }
    }
}