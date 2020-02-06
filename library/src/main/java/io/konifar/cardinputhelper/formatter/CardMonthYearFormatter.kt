package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.ext.digits

object CardMonthYearFormatter {
    const val SLASH = "/"

    fun format(monthYear: CharSequence): String {
        if (monthYear.isEmpty()) {
            return ""
        }

        val monthYearArray = monthYear.split(SLASH)
        // Slash is not included
        if (monthYearArray.size == 1) {
            if (monthYear.length == 1) {
                return if (monthYear.digits().toInt() <= 1) {
                    monthYear.toString()
                } else {
                    "0$monthYear$SLASH"
                }
            }
            if (monthYear.length >= 2) {
                return if (monthYear.digits().toInt() <= 12) {
                    "$monthYear$SLASH"
                } else {
                    StringBuilder("0$monthYear").insert(2, SLASH).toString()
                }
            }
        }

        // Slash is included
        if (monthYearArray.size == 2) {
            val month = monthYearArray.first()
            val year = monthYearArray.last()

            if (month.isEmpty()) {
                return if (year.isEmpty()) { // "/"
                    ""
                } else {
                    val endIndex = (monthYear.length).coerceAtMost(3)
                    monthYear.substring(0, endIndex)
                }
            }

            val modifiedMonthYear = when {
                month.toInt() in 2..9 && !month.startsWith("0") -> "0${monthYear.digits()}"
                else -> monthYear.digits()
            }

            if (modifiedMonthYear.length > 2) {
                val result = StringBuilder(modifiedMonthYear).insert(2, SLASH).toString()
                val endIndex = (result.length).coerceAtMost(5)
                return result.substring(0, endIndex)
            }
        }

        return monthYear.toString()
    }

    fun formatForDeleted(monthYear: CharSequence, beforeText: String): String {
        if (monthYear.isEmpty() || monthYear == SLASH) {
            return ""
        }

        val isSlashDeleted = beforeText.contains(SLASH) && !monthYear.contains(SLASH)
        if (isSlashDeleted) {
            val indexBeforeSlash = beforeText.indexOf(SLASH) - 1
            return monthYear.replaceRange(indexBeforeSlash, indexBeforeSlash + 1, SLASH).toString()
        }

        return monthYear.toString()
    }
}