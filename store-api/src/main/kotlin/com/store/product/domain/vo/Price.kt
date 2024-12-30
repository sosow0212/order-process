package com.store.product.domain.vo

data class Price(val value: Int) {

    init {
        require(value >= 0) { "Price cannot be negative." }
    }
}
