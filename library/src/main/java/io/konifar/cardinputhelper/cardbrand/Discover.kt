package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 6011 1111 1111 1117
 */
class Discover(
    override val brandPattern: Pattern = Pattern.compile("^6(?:011|5[0-9]{2})$"),
    override val verifyPattern: Pattern = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4)
) : CardBrand