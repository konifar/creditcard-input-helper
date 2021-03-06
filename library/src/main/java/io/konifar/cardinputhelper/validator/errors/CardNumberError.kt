package io.konifar.cardinputhelper.validator.errors

enum class CardNumberError {
    EMPTY,
    NOT_ENOUGH_LENGTH,
    UNSUPPORTED_BRAND,
    INVALID_BRAND_FORMAT,
    INVALID_CARD_NUMBER,
    NONE,
}