package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * Unchecked card
 */
class UnChecked(
    override val brandPattern: Pattern = Pattern.compile("^[0-9]{4}$"),
    override val verifyPattern: Pattern = Pattern.compile("^[0-9]{16}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4),
    override val securityCodeLength: Int = 3
) : CardBrand