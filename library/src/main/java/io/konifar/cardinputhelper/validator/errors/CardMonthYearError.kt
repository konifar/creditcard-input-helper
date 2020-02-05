package io.konifar.cardinputhelper.validator.errors

enum class CardMonthYearError {
    IS_EMPTY,
    MONTH_REQUIRED,
    MONTH_INVALID,
    YEAR_REQUIRED,
    YEAR_INVALID,
    EXPIRED,
    NONE,
}