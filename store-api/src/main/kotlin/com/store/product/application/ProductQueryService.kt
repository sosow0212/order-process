package com.store.product.application

import com.store.product.application.port.`in`.ProductQueryUseCase
import com.store.product.application.port.`in`.command.ProductPagingCommand
import com.store.product.application.port.`in`.command.ProductQueryCommand
import com.store.product.application.port.out.ProductRepositoryPort
import com.store.product.domain.Product
import org.springframework.stereotype.Service

@Service
class ProductQueryService(
    private val productRepositoryPort: ProductRepositoryPort
) : ProductQueryUseCase {

    // TODO : Domain dto에 필요한 컬럼만 추가해서 반환해야하지만, 추후 변경 예정
    override fun findAllProductWithPaging(command: ProductPagingCommand): List<Product> {
        val response = productRepositoryPort.findAllWithPaging(
            offset = command.offset,
            limit = command.limit
        )

        return response
    }

    // TODO : 조회수 증감, 동시성 문제가 있지만 이 부분도 추후 변경 예정
    override fun findProduct(command: ProductQueryCommand): Product {
        val product = (productRepositoryPort.findById(command.productId)
            ?: throw IllegalArgumentException("Product with id ${command.productId} not found"))

        product.view()
        productRepositoryPort.save(product)
        return product
    }
}
