package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CardBrandTest {

    @RunWith(Parameterized::class)
    class From(
        private val cardNumber: CharSequence,
        private val supportedCardType: Array<CardBrand>,
        private val output: CardBrand
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(
                        "4242",
                        arrayOf(Visa()),
                        Visa()
                    ),
                    arrayOf(
                        "424",
                        arrayOf(Visa()),
                        Unchecked()
                    ),
                    arrayOf(
                        "1234",
                        arrayOf(Visa()),
                        Unchecked()
                    ),
                    arrayOf(
                        "5555",
                        arrayOf(Visa()),
                        UnSupported()
                    ),
                    arrayOf(
                        "5555",
                        arrayOf(Visa(), Mastercard()),
                        Mastercard()
                    )
                )
            }
        }

        @Test
        fun from() {
            val actual = CardBrand.from(cardNumber, supportedCardType)
            assertEquals(output::javaClass.name, actual::javaClass.name)
        }
    }

    @RunWith(Parameterized::class)
    class MatchBrand(
        private val subject: CardBrand,
        private val input: CharSequence,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(Visa(), "424", false),
                    arrayOf(Visa(), "4242", true),
                    arrayOf(Visa(), "4242424242424242", true),
                    arrayOf(Mastercard(), "4242", false),
                    arrayOf(Mastercard(), "5555", true)
                )
            }
        }

        @Test
        fun matchBrand() {
            val actual = subject.matchBrand(input)
            assertEquals(output, actual)
        }
    }
}