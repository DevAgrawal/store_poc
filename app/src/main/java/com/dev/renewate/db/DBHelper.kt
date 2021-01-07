package com.dev.renewate.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dev.renewate.model.Product

class DBHelper(context: Context) : SQLiteOpenHelper(context, TABLE_PRODUCT, null, 2) {

    companion object {
        var TABLE_PRODUCT = "tableProduct"
        var KEY_ID = "id"
        var KEY_PRODUCT_NAME = "productName"
        var KEY_PRODUCT_PRICE = "productPrice"
        var KEY_GST = "productGST"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val product = "CREATE TABLE $TABLE_PRODUCT ( $KEY_ID INTEGER, " +
                " $KEY_PRODUCT_NAME TEXT, $KEY_PRODUCT_PRICE REAL, $KEY_GST REAL )"

        db?.execSQL(product)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion)
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        onCreate(db)
    }

    fun insertProduct(product: Product) {
        val savedProducts = getAllProduct()
        if (!savedProducts.any { it.id == product.id }) {
            val sqLiteDatabase = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(KEY_ID, product.id)
            contentValues.put(KEY_PRODUCT_NAME, product.name)
            contentValues.put(KEY_PRODUCT_PRICE, product.price)
            contentValues.put(KEY_GST, product.gst)
            sqLiteDatabase.insert(TABLE_PRODUCT, null, contentValues)
        }
    }

    fun getAllProduct(): ArrayList<Product> {
        val products = ArrayList<Product>()
        val db = this.readableDatabase
        val rawQuery = db.rawQuery("SELECT * FROM $TABLE_PRODUCT", null)

        try {
            rawQuery.moveToFirst()

            do {
                products.add(
                    Product(
                        rawQuery.getInt(rawQuery.getColumnIndex(KEY_ID)),
                        rawQuery.getString(rawQuery.getColumnIndex(KEY_PRODUCT_NAME)),
                        rawQuery.getDouble(rawQuery.getColumnIndex(KEY_PRODUCT_PRICE)),
                        rawQuery.getDouble(rawQuery.getColumnIndex(KEY_GST))
                    )
                )
            } while (rawQuery.moveToNext())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return products
    }
}