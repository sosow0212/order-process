package com.store.product.adapter.`in`.response

import com.store.product.domain.Product

data class ProductsPagingResponse(
    val products: List<ProductResponse>,
    val total: Int,
) {
    companion object {
        fun from(result: List<Product>): ProductsPagingResponse? {
            return ProductsPagingResponse(
                products = result.map { ProductResponse.from(it) },
                total = result.size
            )
        }
    }
}
