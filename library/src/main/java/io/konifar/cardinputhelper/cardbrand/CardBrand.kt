package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

interface CardBrand {

    companion object {
        private const val MIN_CARD_CHECK_LENGTH = 4

        val all = arrayOf(
            Visa(),
            Mastercard(),
            Amex(),
            Discover(),
            Diners(),
            Jcb()
        )

        fun from(cardNumber: CharSequence, supportedCardType: Array<CardBrand>): CardBrand {
            val number = removeExceptDigit(cardNumber)
            if (number.length >= MIN_CARD_CHECK_LENGTH) {
                for (cardType in supportedCardType) {
                    if (cardType.matchBrand(number)) {
                        return cardType
                    }
                }
                return UnSupported()
            }
            return Unchecked()
        }

        fun removeExceptDigit(cardNumber: CharSequence): String {
            val builder = StringBuilder()
            for (c in cardNumber) {
                if (c.isDigit()) {
                    builder.append(c)
                }
            }
            return builder.toString()
        }
    }

    val brandPattern: Pattern
    val verifyPattern: Pattern
    val format: IntArray

    fun matchBrand(cardNumber: CharSequence): Boolean {
        return brandPattern.matcher(
            cardNumber.subSequence(
                0,
                MIN_CARD_CHECK_LENGTH
            )
        ).matches()
    }

    fun validateFormatSetting(): Boolean {
        if (format.isEmpty()) {
            throw IllegalStateException("Card:${javaClass.simpleName} format is empty")
        }

        for (i in format) {
            if (i <= 0) {
                throw IllegalStateException("Card: ${javaClass.simpleName} format pattern must contain numbers greater than zero")
            }
        }

        return true
    }

    fun length(): Int {
        var sum = 0
        for (i in format) {
            sum += i
        }
        return sum
    }
}