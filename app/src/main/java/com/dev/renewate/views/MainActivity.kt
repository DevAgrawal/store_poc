package com.dev.renewate.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.renewate.adapter.ProductListAdapter
import com.dev.renewate.R
import com.dev.renewate.model.Product
import com.dev.renewate.viewmodel.ProductViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    var cartProducts = ArrayList<Product>()
    lateinit var adapter: ProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = resources.getString(R.string.product)

        adapter = ProductListAdapter(ArrayList())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        var allProducts = ArrayList<Product>()
        val viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.insertProduct(this)
        viewModel.getAllProduct(this).observe(this, Observer {
            Log.e("Dev", Gson().toJson(it))
            allProducts = it
            adapter.products = it
            adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        })
        adapter.onAddToCartClick = { id ->
            allProducts.firstOrNull { it.id == id }?.let { cartProducts.add(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        val menu_cart = menu?.findItem(R.id.action_cart)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_cart -> {
                var intent = Intent( this@MainActivity, CartActivity::class.java)
                intent.putExtra("products", cartProducts)
                startActivity(intent)
            }
        }
        return true
    }
}