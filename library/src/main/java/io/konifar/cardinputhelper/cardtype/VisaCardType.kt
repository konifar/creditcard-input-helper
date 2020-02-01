package io.konifar.cardinputhelper.cardtype

import java.util.regex.Pattern

/**
 * 4242 4242 4242 4242
 */
class VisaCardType(
    override val brandPattern: Pattern = Pattern.compile("^4[0-9]{3}?"),
    override val verifyPattern: Pattern = Pattern.compile("^4[0-9]{15}?"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4)
) : CardType