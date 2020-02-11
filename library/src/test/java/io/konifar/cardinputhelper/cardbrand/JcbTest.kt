package io.konifar.cardinputhelper.cardbrand

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class JcbTest {

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
                    arrayOf("35", false),
                    arrayOf("356", false),
                    arrayOf("3567", true),
                    arrayOf("4567", false),
                    arrayOf("2567", false),
                    arrayOf("35678", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Jcb().brandPattern.matcher(input).matches()
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
                    arrayOf("356789012345678", false),
                    arrayOf("35678901234567890", false),
                    arrayOf("3567890123456789", true),
                    arrayOf("4567890123456789", false),
                    arrayOf("2567890123456789", false)
                )
            }
        }

        @Test
        fun brandPattern() {
            val actual = Jcb().verifyPattern.matcher(input).matches()
            assertEquals(output, actual)
        }
    }

    class Format {
        @Test
        fun format() {
            val format = Jcb().format
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
            assertEquals(3, Jcb().securityCodeLength)
        }
    }
}