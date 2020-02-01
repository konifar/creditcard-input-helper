package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 3622 7206 2716 67
 */
class Diners(
    override val brandPattern: Pattern = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]$"),
    override val verifyPattern: Pattern = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 2),
    override val securityCodeLength: Int = 3
) : CardBrand