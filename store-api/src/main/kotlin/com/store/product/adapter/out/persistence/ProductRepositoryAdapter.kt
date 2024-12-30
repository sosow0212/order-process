package com.store.product.adapter.out.persistence

import com.store.product.adapter.out.persistence.entity.ProductJpaRepository
import com.store.product.adapter.out.persistence.entity.ProductPersistenceMapper
import com.store.product.adapter.out.persistence.entity.ProductQueryRepository
import com.store.product.application.port.out.ProductRepository
import com.store.product.domain.Product
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ProductRepositoryAdapter(
    private val productPersistenceMapper: ProductPersistenceMapper,
    private val productJpaRepository: ProductJpaRepository,
    private val productQueryRepository: ProductQueryRepository
) : ProductRepository {

    @Transactional
    override fun findById(id: Long): Product? {
        return productJpaRepository.findByIdOrNull(id)
            ?.let { productPersistenceMapper.toDomain(it) }
    }

    @Transactional
    override fun save(product: Product): Product {
        val entity = productPersistenceMapper.toEntity(product)
        val savedEntity = productJpaRepository.save(entity)
        return productPersistenceMapper.toDomain(savedEntity)
    }

    @Transactional(readOnly = true)
    override fun findAllWithPaging(offset: Int?, limit: Int): List<Product> {
        val savedEntities = productQueryRepository.findAllWithPaging(offset, limit)
        return savedEntities.map { productPersistenceMapper.toDomain(it) }
    }
}
