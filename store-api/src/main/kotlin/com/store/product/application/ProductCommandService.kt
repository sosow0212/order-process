package com.store.product.application

import com.store.product.application.port.`in`.ProductCommandUseCase
import com.store.product.application.port.`in`.command.ProductCreateCommand
import com.store.product.application.port.out.ProductRepositoryPort
import com.store.product.domain.Product
import org.springframework.stereotype.Service

@Service
class ProductCommandService(
    private val productRepositoryPort: ProductRepositoryPort
) : ProductCommandUseCase {

    override fun createProduct(command: ProductCreateCommand): Long {
        val product = Product.fromDefaultRule(
            title = command.title,
            content = command.content,
            price = command.price,
            quantity = command.quantity,
        )

        return productRepositoryPort.save(product)
            .id
    }
}
