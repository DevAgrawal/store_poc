package com.dev.renewate.model

import java.io.Serializable

data class Product(var id: Int,
                   var name: String,
                   var price: Double,
                   var gst: Double ): Serializable