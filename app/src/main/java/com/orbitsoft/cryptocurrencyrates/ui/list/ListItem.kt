package com.orbitsoft.cryptocurrencyrates.ui.list

import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUI

sealed class ListItem {
    data class Row(val coinUi: CoinUI) : ListItem()
    object EmptyState : ListItem()
}