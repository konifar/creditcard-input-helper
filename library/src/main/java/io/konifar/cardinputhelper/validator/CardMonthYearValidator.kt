package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter.SLASH
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError
import java.util.*

object CardMonthYearValidator {

    fun validateOnFocusChanged(monthYear: CharSequence): CardMonthYearError {
        val monthYearArray = monthYear.split(SLASH)
        if (monthYearArray.size == 1) {
            return CardMonthYearError.EMPTY
        }

        val month = monthYearArray.first()
        val year = monthYearArray.last()

        checkMonthYear(month, year)?.let {
            return it
        }

        return CardMonthYearError.NONE
    }

    fun validateOnTextChanged(monthYear: CharSequence): CardMonthYearError {
        val monthYearArray = monthYear.split(SLASH)
        if (monthYearArray.size == 2) {
            val month = monthYearArray.first()
            val year = monthYearArray.last()
            if (month.isNotEmpty() && year.isNotEmpty() && year.length == 2) {
                checkMonthYear(month, year)?.let {
                    return it
                }
            }
        }

        return CardMonthYearError.NONE
    }

    private fun checkMonthYear(month: String, year: String): CardMonthYearError? {
        if (month.isEmpty()) return CardMonthYearError.MONTH_REQUIRED
        val monthInt = month.toIntOrNull()
        if (monthInt == null || monthInt > 12 || monthInt <= 0) return CardMonthYearError.MONTH_INVALID

        if (year.isEmpty()) return CardMonthYearError.YEAR_REQUIRED

        val yearInt = year.toIntOrNull() ?: return CardMonthYearError.YEAR_INVALID

        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        if (yearInt > currentYear + 20) return CardMonthYearError.YEAR_OVER_20_YEARS_LATER
        if (yearInt < currentYear || (yearInt == currentYear && monthInt < currentMonth)) return CardMonthYearError.EXPIRED

        return null
    }
}