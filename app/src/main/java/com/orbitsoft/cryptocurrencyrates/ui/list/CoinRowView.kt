package com.orbitsoft.cryptocurrencyrates.ui.list

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.orbitsoft.cryptocurrencyrates.R
import com.orbitsoft.cryptocurrencyrates.databinding.CoinRowViewBinding
import com.orbitsoft.cryptocurrencyrates.ui.glide.SvgSoftwareLayerSetter
import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUI
import java.text.NumberFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

class CoinRowView(
    context: Context
) : FrameLayout(context) {

    private val viewBinding: CoinRowViewBinding by viewBinding(CreateMethod.INFLATE)
    private val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance("USD")
    }
    private val svgRequestBuilder = Glide.with(this)
        .`as`(PictureDrawable::class.java)
        .listener(SvgSoftwareLayerSetter())
        .placeholder(R.drawable.row_logo_placeholder)

    private val imageRequestBuilder = Glide.with(this).asDrawable()
        .placeholder(R.drawable.row_logo_placeholder)

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        initChart()
    }


    /**
     * Prepare chart
     */
    private fun initChart() {
        with(viewBinding.chart) {
            setNoDataText("")
            description = null
            setPadding(0, 0, 0, 0)
            setViewPortOffsets(0f, 0f, 0f, 0f)
            legend.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
        }
    }

    fun bind(coinUi: CoinUI) {
        with(viewBinding) {
            number.text = coinUi.position.toString()
            name.text = coinUi.name
            symbol.text = coinUi.symbol
            price.text = try {
                formatPrice(coinUi.price.toDouble())
            } catch (ex: Exception) {
                formatPrice(0.0)
            }
            marketCap.text = try {
                coinUi.marketCap?.let {
                    formatMarketCap(it.toDouble())
                }
                    ?: formatMarketCap(0.0)
            } catch (ex: java.lang.Exception) {
                formatMarketCap(0.0)
            }

            var colorResId: Int = R.color.row_negative
            try {
                val prefix = if (coinUi.change.toDouble() > 0) {
                    colorResId = R.color.row_positive
                    "+"
                } else {
                    ""
                }
                percent.setTextColor(ContextCompat.getColor(context, colorResId))
                percent.text = String.format(
                    context.getString(R.string.percent), prefix, coinUi.change.toDouble()
                )
            } catch (ex: java.lang.Exception) {
                viewBinding.percent.text = "-"
            }

            if (coinUi.iconUrl?.isNotEmpty() == true) {
                if (coinUi.iconUrl.contains(".svg")) {
                    svgRequestBuilder.load(coinUi.iconUrl)
                        .into(viewBinding.logo)
                } else {
                    imageRequestBuilder.load(coinUi.iconUrl)
                        .into(viewBinding.logo)
                }
            }

            drawChart(coinUi, colorResId)
        }
    }

    private fun formatMarketCap(marketCap: Double): String {
        if (marketCap < 1000) return formatPrice(marketCap)
        val exp = (ln(marketCap) / ln(1000.0)).toInt()
        return String.format(
            "%1s%c",
            formatPrice(marketCap / 1000.0.pow(exp.toDouble())),
            "kMBTPE"[exp - 1]
        )
    }

    private fun formatPrice(price: Double): String {
        return try {
            formatter.format(price)
        } catch (ex: Exception) {
            "-"
        }
    }

    /**
     * Draw chart for this row
     *
     * @param coin
     * @param colorResId
     */
    private fun drawChart(coin: CoinUI, colorResId: Int) {
        with(viewBinding.chart) {
            clear()
            val values = coin.sparkline.filterNotNull().mapIndexed { index, value ->
                Entry(index.toFloat(), value)
            }

            val dataSets: List<ILineDataSet> = listOf(
                LineDataSet(values, "").apply {
                    setDrawValues(false)
                    setDrawCircles(false)
                    setDrawFilled(true)
                    lineWidth = 1.0f
                    fillAlpha = 10
                    fillColor = ContextCompat.getColor(context, colorResId)
                    color = ContextCompat.getColor(context, colorResId)
                }
            )

            // create a data object with the data sets and set
            data = LineData(dataSets)
        }
    }

    fun recycle() {
        Glide.with(context).clear(viewBinding.logo)
    }
}