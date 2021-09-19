package com.orbitsoft.cryptocurrencyrates.ui.list.state

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orbitsoft.cryptocurrencyrates.databinding.StateRowViewBinding

class StateRowView(context: Context, onRetryBtnCallback: () -> Unit) : FrameLayout(context) {

    private val viewBinding: StateRowViewBinding by viewBinding(CreateMethod.INFLATE)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        viewBinding.retryBtn.setOnClickListener {
            onRetryBtnCallback()
        }
    }

    fun bind(loadState: LoadState) {
        with(viewBinding) {
            errorWrapper.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            loadingWrapper.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }
}