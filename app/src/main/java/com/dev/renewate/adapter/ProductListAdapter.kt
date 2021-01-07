package com.dev.renewate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.renewate.R
import com.dev.renewate.model.Product

class ProductListAdapter(var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var onAddToCartClick: ((productId: Int) -> Unit)? = null

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addToCart = itemView.findViewById<Button>(R.id.addToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_adapter, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        when (products[position].id) {
            1 -> holder.imageView.setImageResource(R.drawable.refrigerator)
            2 -> holder.imageView.setImageResource(R.drawable.grinder)
            3 -> holder.imageView.setImageResource(R.drawable.watercooler)
        }
        holder.productName.text = products[position].name
        holder.productPrice.text = products[position].price.toString()
        holder.addToCart.setOnClickListener {
            onAddToCartClick?.invoke(products[position].id)
            holder.addToCart.alpha = 0.5f
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}