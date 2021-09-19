package com.orbitsoft.cryptocurrencyrates.ui.list

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orbitsoft.cryptocurrencyrates.databinding.EmptyStateRowViewBinding

class EmptyStateRowView(
    context: Context,
    onRetryBtnCallback: () -> Unit
) : FrameLayout(context) {

    private val viewBinding: EmptyStateRowViewBinding by viewBinding(CreateMethod.INFLATE)

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        viewBinding.retryBtn.setOnClickListener {
            onRetryBtnCallback()
        }
    }
}