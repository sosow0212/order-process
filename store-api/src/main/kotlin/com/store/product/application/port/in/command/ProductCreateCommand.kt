package com.store.product.application.port.`in`.command

data class ProductCreateCommand(
    val title: String,
    val content: String,
    val price: Int,
    val quantity: Int
)
