package io.konifar.cardinputhelper

import java.util.regex.Pattern

/**
 * Pattern or Issuer identification number
 * https://en.wikipedia.org/wiki/Payment_card_number
 *
 * Sample card data
 * https://stripe.com/docs/testing
 */
enum class Card(
    val brandPattern: Pattern,
    val verifyPattern: Pattern,
    val format: IntArray
) {
    AMEX(
        Pattern.compile("^3[47][0-9]{2}$"),
        Pattern.compile("^3[47][0-9]{13}$"), //
        intArrayOf(4, 6, 5)
    ),
    VISA(
        Pattern.compile("^4[0-9]{3}?"),
        Pattern.compile("^4[0-9]{15}?"),
        intArrayOf(4, 4, 4, 4)
    ),
    MASTERCARD(
        Pattern.compile("^5[1-5][0-9]{2}$"),
        Pattern.compile("^5[1-5][0-9]{14}$"),
        intArrayOf(4, 4, 4, 4)
    ),
    DISCOVER(
        Pattern.compile("^6(?:011|5[0-9]{2})$"),
        Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$"),
        intArrayOf(4, 4, 4, 4)
    ),
    DINERS(
        Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]$"),
        Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
        intArrayOf(4, 4, 4, 2)
    ),
    JCB(
        Pattern.compile("^35[0-9]{2}$"),
        Pattern.compile("^35[0-9]{14}$"),
        intArrayOf(4, 4, 4, 4)
    )
}