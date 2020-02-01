package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 1234 5678 9012 3456
 */
class UnSupported(
    override val brandPattern: Pattern = Pattern.compile("^[0-9]{4}$"),
    override val verifyPattern: Pattern = Pattern.compile("^[0-9]{16}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4),
    override val securityCodeLength: Int = 3
) : CardBrand