package com.store.product.fixture

import com.store.product.domain.Product

class ProductFixture {

    companion object {
        fun 상품_생성(): Product {
            return Product.fromDefaultRule(
                title = "title",
                content = "content",
                price = 100,
                quantity = 100
            )
        }
    }
}
