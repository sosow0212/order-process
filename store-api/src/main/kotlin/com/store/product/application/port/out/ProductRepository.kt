package com.store.product.application.port.out

import com.store.product.domain.Product

interface ProductRepository {

    fun findById(id: Long): Product?

    fun save(product: Product): Product

    fun findAllWithPaging(offset: Int?, limit: Int): List<Product>
}
