package io.konifar.cardinputhelper.ext

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CharSequenceExtTest {

    @RunWith(Parameterized::class)
    class Digits(
        private val subject: CharSequence,
        private val output: String
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", ""),
                    arrayOf("1234", "1234"),
                    arrayOf("1234 ", "1234"),
                    arrayOf("1234-", "1234"),
                    arrayOf("1234.", "1234"),
                    arrayOf("1234/", "1234")
                )
            }
        }

        @Test
        fun from() {
            val actual = subject.digits()
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class DigitsAndSlash(
        private val subject: CharSequence,
        private val output: String
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", ""),
                    arrayOf("1234", "1234"),
                    arrayOf("12/34 ", "12/34"),
                    arrayOf("12 34", "1234")
                )
            }
        }

        @Test
        fun from() {
            val actual = subject.digitsAndSlash()
            assertEquals(output, actual)
        }
    }
}