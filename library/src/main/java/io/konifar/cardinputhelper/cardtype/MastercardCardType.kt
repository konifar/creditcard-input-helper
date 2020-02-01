package io.konifar.cardinputhelper.cardtype

import java.util.regex.Pattern

/**
 * 5555 5555 5555 4444
 */
class MastercardCardType(
    override val brandPattern: Pattern = Pattern.compile("^5[1-5][0-9]{2}$"),
    override val verifyPattern: Pattern = Pattern.compile("^5[1-5][0-9]{14}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4)
) : CardType