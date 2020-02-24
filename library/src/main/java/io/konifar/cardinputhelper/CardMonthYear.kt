package io.konifar.cardinputhelper

import io.konifar.cardinputhelper.ext.digits
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter.SLASH

data class CardMonthYear(
    val month: String,
    val year: String
) {

    companion object {
        fun from(
            monthYear: String,
            monthZeroPadding: Boolean = false,
            yearFullDigits: Boolean = false
        ): CardMonthYear {
            val list = monthYear.split(CardMonthYearFormatter.SLASH)

            // Month
            var month = if (list.isEmpty()) "" else list.first().digits()
            month = if (month.isEmpty()) {
                ""
            } else if (monthZeroPadding) {
                if (month.toInt() in 1..9) "0${month.toInt()}" else month
            } else {
                month.toInt().toString()
            }

            // Year
            var year = if (list.size < 2) "" else list[1]
            year = if (yearFullDigits && year.length == 2) {
                "20$year"
            } else {
                year
            }

            return CardMonthYear(month, year)
        }
    }

    fun formatText() = if (month.isEmpty() && year.isEmpty()) {
        ""
    } else {
        "$month$SLASH$year"
    }
}