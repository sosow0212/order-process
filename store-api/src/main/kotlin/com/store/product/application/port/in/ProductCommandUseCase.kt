package com.store.product.application.port.`in`

import com.store.product.application.port.`in`.command.ProductCreateCommand

interface ProductCommandUseCase {

    fun createProduct(command: ProductCreateCommand): Long
}
