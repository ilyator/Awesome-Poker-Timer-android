package com.ily.pakertymer.util

import android.content.Context

/**
 * Created by ily on 22.08.2016.
 */
object MeasureUtil {

    fun dpToPx(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return Math.round(dp * scale)
    }

    fun pxToDp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return Math.round(px / scale)
    }

}
