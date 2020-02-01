package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 3782 822463 10005
 */
class Amex(
    override val brandPattern: Pattern = Pattern.compile("^3[47][0-9]{2}$"),
    override val verifyPattern: Pattern = Pattern.compile("^3[47][0-9]{13}$"),
    override val format: IntArray = intArrayOf(4, 6, 5),
    override val securityCodeLength: Int = 4
) : CardBrand