package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class DiscoverTest {

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
                    arrayOf("6", false),
                    arrayOf("60", false),
                    arrayOf("601", false),
                    arrayOf("6011", true),
                    arrayOf("6012", false),
                    arrayOf("6500", true),
                    arrayOf("6600", false),
                    arrayOf("6400", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Discover().brandPattern.matcher(input).matches()
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
                    arrayOf("601123456789012", false),
                    arrayOf("60112345678901234", false),
                    arrayOf("6011234567890123", true),
                    arrayOf("6500234567890123", true),
                    arrayOf("6600234567890123", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Discover().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Discover().format
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
            assertEquals(3, Discover().securityCodeLength)
        }
    }
}