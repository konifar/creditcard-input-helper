package io.konifar.cardinputhelper.validator.errors

enum class CardMonthYearError {
    EMPTY,
    MONTH_REQUIRED,
    MONTH_INVALID,
    YEAR_REQUIRED,
    YEAR_OVER_20_YEARS_LATER,
    EXPIRED,
    NONE,
}