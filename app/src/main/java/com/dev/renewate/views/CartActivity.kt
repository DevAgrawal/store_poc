package com.dev.renewate.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.renewate.R
import com.dev.renewate.model.Product

class CartActivity : AppCompatActivity() {

    var orderAmount: Double = 0.00
    var gstAmount: Double = 0.00
    var totalAmount: Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        title = resources.getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cartProducts = intent.getSerializableExtra("products") as ArrayList<Product>


        for (i in 0 until cartProducts.size) {
            orderAmount = orderAmount + cartProducts[i].price
            gstAmount = gstAmount + cartProducts[i].gst
        }

        totalAmount = orderAmount + gstAmount

        findViewById<TextView>(R.id.orderAmount).text =
            resources.getString(R.string.rupee_sign) + " " + orderAmount.toString()
        findViewById<TextView>(R.id.gstAmount).text =
            resources.getString(R.string.rupee_sign) + " " + gstAmount.toString()
        findViewById<TextView>(R.id.totalAmount).text =
            resources.getString(R.string.rupee_sign) + " " + totalAmount.toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}