package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.ext.digits

object CardMonthYearFormatter {
    const val SLASH = "/"

    fun format(yearMonth: CharSequence): String {
        return when (yearMonth.toString()) {
            SLASH -> ""
            "2", "3", "4", "5", "6", "7", "8", "9" -> "0$yearMonth$SLASH"
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" -> "$yearMonth$SLASH"
            else -> {
                if (yearMonth.length >= 4 && yearMonth.last().toString() == SLASH) {
                    return StringBuilder(yearMonth.digits()).insert(2, SLASH).toString()
                }
                return yearMonth.toString()
            }
        }
    }
}