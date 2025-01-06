package com.store.product.application

import com.store.product.application.port.`in`.command.ProductCreateCommand
import com.store.product.application.port.out.ProductRepositoryPort
import com.store.product.fixture.ProductFixture.Companion.상품_생성
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertDoesNotThrow

class ProductCommandServiceTest : BehaviorSpec({

    val productRepositoryPort: ProductRepositoryPort = mockk()
    val productCommandService = ProductCommandService(productRepositoryPort)

    Given("상품을 생성시") {
        When("예외가 없다면") {
            every { productRepositoryPort.save(any()) } returns 상품_생성()

            Then("정상 생성된다") {
                assertDoesNotThrow {
                    productCommandService.createProduct(
                        ProductCreateCommand(
                            "title",
                            "content",
                            100,
                            100
                        )
                    )
                }
            }
        }
    }
})
