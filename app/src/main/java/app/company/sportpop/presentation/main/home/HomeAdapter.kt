package app.company.sportpop.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.company.sportpop.core.extensions.loadFromUrl
import app.company.sportpop.databinding.RowHomeBinding
import app.company.sportpop.domain.entities.Product

class HomeAdapter(private val clickListener: (Product) -> Unit) : ListAdapter<Product, HomeAdapter.ViewHolder>(VenueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowHomeBinding = RowHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bindTo(product)
        holder.itemView.setOnClickListener { clickListener(product) }
    }

    class VenueDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.uid == newItem.uid

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    inner class ViewHolder(private val binding: RowHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(product: Product) {
            binding.txtTitle.text = product.title
            binding.txtPrice.text = "${product.price} €"
            binding.imageView.loadFromUrl(product.photoUrl)
        }
    }
}
