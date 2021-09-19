package com.orbitsoft.cryptocurrencyrates.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import com.orbitsoft.cryptocurrencyrates.R
import com.orbitsoft.cryptocurrencyrates.data.Order
import com.orbitsoft.cryptocurrencyrates.databinding.HeaderViewBinding

class HeaderView : FrameLayout {

    private val viewBinding: HeaderViewBinding by viewBinding(CreateMethod.INFLATE)
    var onChangeOrderCallback: (Order) -> Unit = {}

    var order: Order? = null
        set(value) {
            if (value != null && field != value) {
                field = value
                hideOrder()
                order?.let {
                    setArrowIconToView(
                        button = when (value.field) {
                            Order.OrderField.MARKET_CAP -> viewBinding.headerPrice
                            Order.OrderField.CHANGE -> viewBinding.header24h
                        },
                        direction = value.direction
                    )
                }
            }
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        with(viewBinding) {
            headerPrice.setOnClickListener {
                changeOrder(Order.OrderField.MARKET_CAP)
            }

            header24h.setOnClickListener {
                changeOrder(Order.OrderField.CHANGE)
            }
        }
    }

    private fun changeOrder(field: Order.OrderField) {
        val newDirection = if (order?.field == field) {
            when (order?.direction) {
                Order.Direction.ASC -> Order.Direction.DESC
                Order.Direction.DESC -> Order.Direction.ASC
                null -> Order.Direction.DESC
            }
        } else
            Order.Direction.DESC

        onChangeOrderCallback(
            Order(
                field = field,
                direction = newDirection
            )
        )
    }


    private fun setArrowIconToView(button: MaterialButton, direction: Order.Direction) {
        button.icon = ContextCompat.getDrawable(
            context, when (direction) {
                Order.Direction.ASC -> R.drawable.ic_arrow_drop_up
                Order.Direction.DESC -> R.drawable.ic_arrow_drop_down
            }
        )
    }

    /**
     * Hide all order arrows
     */
    private fun hideOrder() {
        with(viewBinding) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_empty)
            headerCryptocurrency.icon = drawable
            header24h.icon = drawable
            headerPrice.icon = drawable
        }
    }
}