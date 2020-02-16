package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.cardbrand.Amex
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.cardbrand.Visa
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CardNumberFormatterTest(
    private val cardNumber: CharSequence,
    private val cardBrand: CardBrand,
    private val separatorType: CardNumberSeparatorType,
    private val output: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<out Any?>> {
            return listOf(
                arrayOf(
                    "",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    ""
                ),
                arrayOf(
                    "4242",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242"
                ),
                arrayOf(
                    "42424",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4"
                ),
                arrayOf(
                    "4242 42424",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4242 4"
                ),
                arrayOf(
                    "4242-4242-4",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4242 4"
                ),
                arrayOf(
                    "4242 4242 4242 4242",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4242 4242 4242"
                ),
                arrayOf(
                    "4242424242424242",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4242 4242 4242"
                ),
                arrayOf(
                    "42424242424242424",
                    Visa(),
                    CardNumberSeparatorType.SPACE,
                    "4242 4242 4242 4242"
                ),
                arrayOf(
                    "4242424242424242",
                    Visa(),
                    CardNumberSeparatorType.HYPHEN,
                    "4242-4242-4242-4242"
                ),
                arrayOf(
                    "378282246310005",
                    Amex(),
                    CardNumberSeparatorType.SPACE,
                    "3782 822463 10005"
                ),
                arrayOf(
                    "3782",
                    Amex(),
                    CardNumberSeparatorType.SPACE,
                    "3782"
                ),
                arrayOf(
                    "3782 8224631",
                    Amex(),
                    CardNumberSeparatorType.SPACE,
                    "3782 822463 1"
                )
            )
        }
    }

    @Test
    fun format() {
        val actual = CardNumberFormatter.format(cardNumber, cardBrand, separatorType)
        assertEquals(output, actual)
    }
}