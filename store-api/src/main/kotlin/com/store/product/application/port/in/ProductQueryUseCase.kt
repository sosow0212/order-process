package com.store.product.application.port.`in`

import com.store.product.application.port.`in`.command.ProductPagingCommand
import com.store.product.application.port.`in`.command.ProductQueryCommand
import com.store.product.domain.Product

interface ProductQueryUseCase {

    fun findAllProductWithPaging(command: ProductPagingCommand): List<Product>

    fun findProduct(command: ProductQueryCommand): Product
}
