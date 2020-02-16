package io.konifar.cardinputhelper.validator.errors

enum class CardMonthYearError {
    EMPTY,
    MONTH_REQUIRED,
    MONTH_INVALID,
    YEAR_REQUIRED,
    YEAR_INVALID,
    EXPIRED,
    NONE,
}