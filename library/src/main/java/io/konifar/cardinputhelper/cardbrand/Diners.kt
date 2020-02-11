package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 3622 7206 2716 67
 */
class Diners(
    override val brandPattern: Pattern = Pattern.compile("^3((?:0[0-5]|[689][0-9])[0-9]|095)$"),
    override val verifyPattern: Pattern = Pattern.compile("^3((?:0[0-5]|[689][0-9])[0-9]|095)[0-9]{10}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 2),
    override val securityCodeLength: Int = 3
) : CardBrand