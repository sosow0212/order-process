package com.store.product.domain

import com.store.product.domain.vo.Price
import com.store.product.domain.vo.Quantity
import com.store.product.domain.vo.ViewCount

class Product(
    val id: Long = 0,
    val title: String,
    val content: String,
    val price: Price,
    val quantity: Quantity,
    val viewCount: ViewCount,
) {
    fun sell() {
        this.quantity.decrease()
    }

    fun view() {
        viewCount.view()
    }

    companion object {
        fun fromDefaultRule(
            title: String, content: String,
            price: Int, quantity: Int,
        ): Product {
            return Product(
                title = title, content = content,
                price = Price(price), quantity = Quantity(quantity),
                viewCount = ViewCount.createDefault()
            )
        }
    }
}
