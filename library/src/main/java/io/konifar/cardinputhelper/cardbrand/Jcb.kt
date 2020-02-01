package io.konifar.cardinputhelper.cardbrand

import java.util.regex.Pattern

/**
 * 3566 0020 2036 0505
 */
class Jcb(
    override val brandPattern: Pattern = Pattern.compile("^35[0-9]{2}$"),
    override val verifyPattern: Pattern = Pattern.compile("^35[0-9]{14}$"),
    override val format: IntArray = intArrayOf(4, 4, 4, 4)
) : CardBrand