package com.orbitsoft.cryptocurrencyrates.data.db

import android.util.Log
import androidx.room.TypeConverter
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.data.Order.Companion.defaultDirection
import com.orbitsoft.cryptocurrencyrates.data.Order.Companion.defaultField
import java.util.*

class Converters {

    private val listSeparator = ";"

    @TypeConverter
    fun fromListOfFloatsToString(list: List<Float?>): String {
        val str = StringBuilder()
        for (value in list) {
            if (value == null) continue
            str.append(if (str.isEmpty()) "" else listSeparator)
            str.append(value.toString())
        }
        return str.toString()
    }

    @TypeConverter
    fun fromStringToListOfFloats(str: String): List<Float> {
        val list = ArrayList<Float>()
        val listStr: Array<String> = str.split(listSeparator.toRegex()).toTypedArray()
        for (value in listStr) {
            list.add(value.toFloat())
        }
        return list
    }

    @TypeConverter
    fun fromOrderToString(order: Order): String {
        val fieldStr = when (order.field) {
            Order.OrderField.MARKET_CAP -> ORDER_MARKET_CAP
            Order.OrderField.CHANGE -> ORDER_CHANGE
        }
        val directionStr = when (order.direction) {
            Order.Direction.ASC -> ORDER_DIRECTION_ASC
            Order.Direction.DESC -> ORDER_DIRECTION_DESC
        }
        return "${fieldStr}_$directionStr"
    }

    @TypeConverter
    fun fromStringToOrder(str: String): Order {
        val values = str.split("_")
        return if (values.size == 2) {
            Order(
                field = when (values[0]) {
                    ORDER_MARKET_CAP -> Order.OrderField.MARKET_CAP
                    ORDER_CHANGE -> Order.OrderField.CHANGE
                    else -> defaultField()
                },
                direction = when (values[1]) {
                    ORDER_DIRECTION_ASC -> Order.Direction.ASC
                    ORDER_DIRECTION_DESC -> Order.Direction.DESC
                    else -> defaultDirection()
                }
            )
        } else Order()
    }

    companion object {
        private const val ORDER_MARKET_CAP = "marketCap"
        private const val ORDER_CHANGE = "change"
        private const val ORDER_DIRECTION_ASC = "asc"
        private const val ORDER_DIRECTION_DESC = "desc"
    }
}