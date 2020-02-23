package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 5555 5555 5555 4444
 * 2223 0031 2200 3222
 * 2210-2720
 */
class Mastercard(
    override val brandPattern: Pattern = Pattern.compile("^(5[1-5][0-9]{2}|2[2-7][0-9]{2})$"),
    override val verifyPattern: Pattern = Pattern.compile("^(5[1-5][0-9]{14}|2[2-7][0-9]{14})$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4),
    override val securityCodeLength: Int = 3
) : CardBrand