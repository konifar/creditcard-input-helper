package io.konifar.cardinputhelper.cardtype

import java.util.regex.Pattern

/**
 * 3782 822463 10005
 */
class AmexCardType(
    override val brandPattern: Pattern = Pattern.compile("^3[47][0-9]{2}$"),
    override val verifyPattern: Pattern = Pattern.compile("^3[47][0-9]{13}$"),
    override val format: IntArray = intArrayOf(4, 6, 5)
) : CardType