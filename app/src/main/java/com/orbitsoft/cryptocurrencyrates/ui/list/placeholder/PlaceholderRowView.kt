package com.orbitsoft.cryptocurrencyrates.ui.list.placeholder

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orbitsoft.cryptocurrencyrates.databinding.PlaceholderRowViewBinding

class PlaceholderRowView(context: Context) : FrameLayout(context) {

    private val viewBinding: PlaceholderRowViewBinding by viewBinding(CreateMethod.INFLATE)

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun bind() {
        viewBinding.shimmerLayout.startShimmer()
    }

    fun recycle() {
        viewBinding.shimmerLayout.stopShimmer()
    }
}