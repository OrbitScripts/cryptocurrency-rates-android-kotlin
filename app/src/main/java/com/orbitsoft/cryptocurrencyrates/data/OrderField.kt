package com.orbitsoft.cryptocurrencyrates.data

data class Order(
    val field: OrderField = defaultField(),
    val direction: Direction = defaultDirection()
) {

    enum class OrderField {
        MARKET_CAP, CHANGE
    }

    enum class Direction {
        ASC, DESC
    }

    companion object {
        fun defaultField() = OrderField.MARKET_CAP
        fun defaultDirection() = Direction.DESC
    }
}