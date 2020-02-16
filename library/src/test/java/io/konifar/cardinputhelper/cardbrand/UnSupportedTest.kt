package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class UnSupportedTest {

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
                    arrayOf("1", false),
                    arrayOf("12", false),
                    arrayOf("123", false),
                    arrayOf("1234", true),
                    arrayOf("12345", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = UnSupported().brandPattern.matcher(input).matches()
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
                    arrayOf("123456789012345", false),
                    arrayOf("1234567890123456", true),
                    arrayOf("12345678901234567", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = UnSupported().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = UnSupported().format
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
            assertEquals(3, UnSupported().securityCodeLength)
        }
    }
}