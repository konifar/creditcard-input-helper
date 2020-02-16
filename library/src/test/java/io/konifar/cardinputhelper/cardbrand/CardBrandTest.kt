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

    @RunWith(Parameterized::class)
    class Length(
        private val subject: CardBrand,
        private val output: Int
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(Visa(), 16),
                    arrayOf(Mastercard(), 16),
                    arrayOf(Amex(), 15)
                )
            }
        }

        @Test
        fun matchBrand() {
            val actual = subject.length()
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class HasEnoughNumberLength(
        private val subject: CardBrand,
        private val input: CharSequence,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(Visa(), "4242424242424242", true),
                    arrayOf(Visa(), "424242424242424", false),
                    arrayOf(Visa(), "42424242424242424", true),
                    arrayOf(Visa(), "4242-4242-4242-424", false),
                    arrayOf(Visa(), "4242-4242-4242-4242", true),
                    arrayOf(Visa(), "4242 4242 4242 424", false),
                    arrayOf(Visa(), "4242 4242 4242 4242", true),
                    arrayOf(Amex(), "378282246310005", true),
                    arrayOf(Amex(), "37828224631000", false),
                    arrayOf(Amex(), "3782 822463 10005", true)
                )
            }
        }

        @Test
        fun hasEnoughNumberLength() {
            val actual = subject.hasEnoughNumberLength(input)
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class HasEnoughSecurityCodeLength(
        private val subject: CardBrand,
        private val input: CharSequence,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(Visa(), "123", true),
                    arrayOf(Visa(), "12", false),
                    arrayOf(Visa(), "1234", true),
                    arrayOf(Amex(), "1234", true),
                    arrayOf(Amex(), "123", false),
                    arrayOf(Amex(), "12345", true)
                )
            }
        }

        @Test
        fun hasEnoughNumberLength() {
            val actual = subject.hasEnoughSecurityCodeLength(input)
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class IsValidFormat(
        private val subject: CardBrand,
        private val input: CharSequence,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(Visa(), "4242424242424242", true),
                    arrayOf(Visa(), "424242424242424", false),
                    arrayOf(Visa(), "4242 4242 4242 4242", true),
                    arrayOf(Visa(), "42424242424242424", false),
                    arrayOf(Amex(), "378282246310005", true),
                    arrayOf(Amex(), "37828224631000", false),
                    arrayOf(Amex(), "3782 822463 10005", true),
                    arrayOf(Amex(), "3782822463100056", false)
                )
            }
        }

        @Test
        fun isValidFormat() {
            val actual = subject.isValidFormat(input)
            assertEquals(output, actual)
        }
    }
}