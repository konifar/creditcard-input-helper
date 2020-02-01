package io.konifar.cardinputhelper

import java.util.regex.Pattern

/**
 * Pattern or Issuer identification number
 * https://en.wikipedia.org/wiki/Payment_card_number
 *
 * Sample card data
 * https://stripe.com/docs/testing
 */
enum class CardType(
    val brandPattern: Pattern,
    val verifyPattern: Pattern,
    val format: IntArray
) {

    // 3782 822463 10005
    AMEX(
        Pattern.compile("^3[47][0-9]{2}$"),
        Pattern.compile("^3[47][0-9]{13}$"),
        intArrayOf(4, 6, 5)
    ),
    // 4242 4242 4242 4242
    VISA(
        Pattern.compile("^4[0-9]{3}?"),
        Pattern.compile("^4[0-9]{15}?"),
        intArrayOf(4, 4, 4, 4)
    ),
    // 5555 5555 5555 4444
    MASTERCARD(
        Pattern.compile("^5[1-5][0-9]{2}$"),
        Pattern.compile("^5[1-5][0-9]{14}$"),
        intArrayOf(4, 4, 4, 4)
    ),
    // 6011 1111 1111 1117
    DISCOVER(
        Pattern.compile("^6(?:011|5[0-9]{2})$"),
        Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$"),
        intArrayOf(4, 4, 4, 4)
    ),
    // 3622 7206 2716 67
    DINERS(
        Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]$"),
        Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
        intArrayOf(4, 4, 4, 2)
    ),
    // 3566 0020 2036 0505
    JCB(
        Pattern.compile("^35[0-9]{2}$"),
        Pattern.compile("^35[0-9]{14}$"),
        intArrayOf(4, 4, 4, 4)
    ),
    // 1234 5678 9012 3456
    OTHER(
        Pattern.compile("^[0-9]{4}$"),
        Pattern.compile("^[0-9]{16}$"),
        intArrayOf(4, 4, 4, 4)
    );

    fun matchBrand(cardNumber: CharSequence): Boolean {
        return brandPattern.matcher(cardNumber.subSequence(0, MIN_CARD_CHECK_LENGTH)).matches()
    }

    fun validateFormatSetting(): Boolean {
        if (format.isEmpty()) {
            throw IllegalStateException("Card:${name} format is empty")
        }

        for (i in format) {
            if (i <= 0) {
                throw IllegalStateException("Card: ${name} format pattern must contain numbers greater than zero");
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

    companion object {
        private const val MIN_CARD_CHECK_LENGTH = 4

        fun from(cardNumber: CharSequence, supportedCard: Array<CardType> = values()): CardType {
            val number = removeExceptDigit(cardNumber)
            if (number.length >= MIN_CARD_CHECK_LENGTH) {
                for (card in supportedCard) {
                    if (card.matchBrand(cardNumber)) {
                        return card
                    }
                }
            }
            return OTHER
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

}