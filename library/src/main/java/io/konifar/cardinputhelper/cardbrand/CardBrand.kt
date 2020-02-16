package io.konifar.cardinputhelper.cardbrand

import io.konifar.cardinputhelper.ext.digits
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

        fun from(cardNumber: CharSequence, supportedCardBrand: Array<CardBrand>): CardBrand {
            val number = cardNumber.digits()
            if (number.length >= MIN_CARD_CHECK_LENGTH) {
                for (cardBrand in supportedCardBrand) {
                    if (cardBrand.matchBrand(number)) {
                        return cardBrand
                    }
                }
                return UnSupported()
            }
            return Unchecked()
        }
    }

    val brandPattern: Pattern
    val verifyPattern: Pattern
    val format: IntArray
    val securityCodeLength: Int

    fun matchBrand(cardNumber: CharSequence): Boolean {
        if (cardNumber.length < MIN_CARD_CHECK_LENGTH) {
            return false
        }
        return brandPattern.matcher(cardNumber.subSequence(0, MIN_CARD_CHECK_LENGTH)).matches()
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

    fun hasEnoughNumberLength(cardNumber: CharSequence) = cardNumber.digits().length >= length()

    fun hasEnoughSecurityCodeLength(code: CharSequence) = code.length >= securityCodeLength

    fun isValidFormat(number: CharSequence) = verifyPattern.matcher(number.digits()).matches()
}