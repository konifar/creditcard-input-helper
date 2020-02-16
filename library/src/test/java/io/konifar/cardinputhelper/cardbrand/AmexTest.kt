package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class AmexTest {

    @RunWith(Parameterized::class)
    class BrandPattern(
        private val input: String,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", false),
                    arrayOf("3", false),
                    arrayOf("34", false),
                    arrayOf("345", false),
                    arrayOf("3456", true),
                    arrayOf("3556", false),
                    arrayOf("3712", true),
                    arrayOf("37123", false),
                    arrayOf("4712", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Amex().brandPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class VerifyPattern(
        private val input: String,
        private val output: Boolean
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", false),
                    arrayOf("34567890123456", false),
                    arrayOf("3456789012345678", false),
                    arrayOf("345678901234567", true),
                    arrayOf("37890123456789", false),
                    arrayOf("3789012345678901", false),
                    arrayOf("378901234567890", true)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Amex().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Amex().format
            assertEquals(3, format.size)
            assertEquals(4, format[0])
            assertEquals(6, format[1])
            assertEquals(5, format[2])
        }
    }

    class SecurityCode {
        @Test
        fun securityCodeLength() {
            assertEquals(4, Amex().securityCodeLength)
        }
    }
}