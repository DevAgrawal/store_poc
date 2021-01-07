package com.dev.renewate.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.renewate.db.DBHelper
import com.dev.renewate.model.Product
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProductViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val sJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + sJob

    var _products: MutableLiveData<ArrayList<Product>>? = null

    fun getAllProduct(context: Context): LiveData<ArrayList<Product>> {
        if (_products == null) {
            _products = MutableLiveData<ArrayList<Product>>()
            loadProductsFromDb(context)
        }
        return _products as MutableLiveData<ArrayList<Product>>
    }

    private fun loadProductsFromDb(context: Context) {
        launch {
            val products: ArrayList<Product>
            val db = DBHelper(context)
            products = db.getAllProduct()
            _products?.value = products
        }
    }

    fun insertProduct(context: Context) {
        val db = DBHelper(context)
        db.insertProduct(Product(2, "Grinder", 1500.00, 430.00))
        db.insertProduct(Product(3, "Watercooler", 660.00, 192.00))
        db.insertProduct(Product(1, "Refrigerator", 11990.00, 3357.00))
    }

    override fun onCleared() {
        sJob.cancelChildren()
        super.onCleared()
    }
}