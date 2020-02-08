package io.konifar.cardinputhelper.formatter

enum class CardNumberSeparatorType(val character: Char) {
    SPACE(" ".toCharArray().first()),
    HYPHEN("-".toCharArray().first())
}