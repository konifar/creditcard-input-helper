package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class MastercardTest {

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
                    arrayOf("5", false),
                    arrayOf("51", false),
                    arrayOf("512", false),
                    arrayOf("5123", true),
                    arrayOf("6123", false),
                    arrayOf("4123", false),
                    arrayOf("5512", true),
                    arrayOf("5612", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Mastercard().brandPattern.matcher(input).matches()
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
                    arrayOf("512345678901234", false),
                    arrayOf("51234567890123456", false),
                    arrayOf("5123456789012345", true),
                    arrayOf("5623456789012345", false),
                    arrayOf("6123456789012345", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Mastercard().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Mastercard().format
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
            assertEquals(3, Mastercard().securityCodeLength)
        }
    }
}