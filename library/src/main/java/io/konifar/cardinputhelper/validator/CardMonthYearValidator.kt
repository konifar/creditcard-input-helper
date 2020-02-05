package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError
import java.util.*

object CardMonthYearValidator {

    fun validateOnFocusChanged(monthYear: CharSequence): CardMonthYearError {
        if (!validateEmpty(monthYear.toString())) return CardMonthYearError.IS_EMPTY
        return CardMonthYearError.NONE
    }

    fun validateOnTextChanged(monthYear: CharSequence): CardMonthYearError {
        if (!validateEmpty(monthYear.toString())) return CardMonthYearError.IS_EMPTY

        checkMonthYear(monthYear.toString())?.let {
            return it
        }

        return CardMonthYearError.NONE
    }

    private fun validateEmpty(monthYear: String) = monthYear.isNotEmpty()

    private fun checkMonthYear(monthYear: String): CardMonthYearError? {
        val monthYearArray = monthYear.split(CardMonthYearFormatter.SLASH)
        if (monthYearArray.isEmpty()) return null

        val month = monthYearArray.first()
        if (month.isEmpty()) return CardMonthYearError.MONTH_REQUIRED
        if (month.toInt() > 12) return CardMonthYearError.MONTH_INVALID

        val year = monthYearArray.last()
        if (year.isEmpty()) return CardMonthYearError.YEAR_REQUIRED

        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        if (year.toInt() > currentYear + 20) return CardMonthYearError.YEAR_INVALID
        if (year.toInt() <= currentYear && month.toInt() <= currentMonth) return CardMonthYearError.EXPIRED

        return null
    }
}