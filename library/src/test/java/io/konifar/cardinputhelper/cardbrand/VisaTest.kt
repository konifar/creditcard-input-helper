package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class VisaTest {

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
                    arrayOf("4", false),
                    arrayOf("45", false),
                    arrayOf("456", false),
                    arrayOf("4567", true),
                    arrayOf("5567", false),
                    arrayOf("3567", false),
                    arrayOf("45678", false),
                    arrayOf("4242", true)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Visa().brandPattern.matcher(input).matches()
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
                    arrayOf("456789012345678", false),
                    arrayOf("45678901234567890", false),
                    arrayOf("4567890123456789", true),
                    arrayOf("5567890123456789", false),
                    arrayOf("3567890123456789", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Visa().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Visa().format
            assertEquals(4, format.size)
            assertEquals(4, format[0])
            assertEquals(4, format[1])
            assertEquals(4, format[2])
            assertEquals(4, format[3])
        }
    }

    class SecurityCode {
        @Test
        fun securityCodeLength() {
            assertEquals(3, Visa().securityCodeLength)
        }
    }
}