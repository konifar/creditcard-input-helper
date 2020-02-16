package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class DinersTest {

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
                    arrayOf("30", false),
                    arrayOf("300", false),
                    arrayOf("3001", true),
                    arrayOf("3101", false),
                    arrayOf("3020", true),
                    arrayOf("3030", true),
                    arrayOf("3040", true),
                    arrayOf("3050", true),
                    arrayOf("3060", false),
                    arrayOf("30600", false),
                    arrayOf("3085", false),
                    arrayOf("3095", true),
                    arrayOf("3096", false),
                    arrayOf("3094", false),
                    arrayOf("3601", true),
                    arrayOf("3701", false),
                    arrayOf("3801", true),
                    arrayOf("3901", true)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Diners().brandPattern.matcher(input).matches()
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
                    arrayOf("3001234567890", false),
                    arrayOf("300123456789012", false),
                    arrayOf("30012345678901", true),
                    arrayOf("30952345678901", true),
                    arrayOf("30752345678901", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Diners().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Diners().format
            assertEquals(3, format.size)
            assertEquals(4, format[0])
            assertEquals(6, format[1])
            assertEquals(4, format[2])
        }
    }

    class SecurityCode {
        @Test
        fun securityCodeLength() {
            assertEquals(3, Diners().securityCodeLength)
        }
    }
}