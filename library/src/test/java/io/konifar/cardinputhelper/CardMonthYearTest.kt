package io.konifar.cardinputhelper

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CardMonthYearTest {

    @RunWith(Parameterized::class)
    class From(
        private val monthYear: String,
        private val monthZeroPadding: Boolean,
        private val yearFullDigits: Boolean,
        private val output: CardMonthYear
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", false, false, CardMonthYear("", "")),
                    arrayOf("/", false, false, CardMonthYear("", "")),
                    // Month
                    arrayOf("0/", false, false, CardMonthYear("0", "")),
                    arrayOf("0/", true, false, CardMonthYear("0", "")),
                    arrayOf("01/", false, false, CardMonthYear("1", "")),
                    arrayOf("01/", true, false, CardMonthYear("01", "")),
                    arrayOf("1/", false, false, CardMonthYear("1", "")),
                    arrayOf("1/", true, false, CardMonthYear("01", "")),
                    arrayOf("10/", false, false, CardMonthYear("10", "")),
                    arrayOf("10/", true, false, CardMonthYear("10", "")),
                    // Year
                    arrayOf("/0", false, false, CardMonthYear("", "0")),
                    arrayOf("/0", false, true, CardMonthYear("", "0")),
                    arrayOf("/1", false, false, CardMonthYear("", "1")),
                    arrayOf("/1", false, true, CardMonthYear("", "1")),
                    arrayOf("/01", false, false, CardMonthYear("", "01")),
                    arrayOf("/01", false, true, CardMonthYear("", "2001")),
                    arrayOf("/30", false, false, CardMonthYear("", "30")),
                    arrayOf("/30", false, true, CardMonthYear("", "2030")),
                    // Month/Year
                    arrayOf("10/30", false, false, CardMonthYear("10", "30")),
                    arrayOf("10/30", true, false, CardMonthYear("10", "30")),
                    arrayOf("10/30", false, true, CardMonthYear("10", "2030")),
                    arrayOf("10/30", true, true, CardMonthYear("10", "2030")),
                    arrayOf("01/30", false, false, CardMonthYear("1", "30")),
                    arrayOf("01/30", true, false, CardMonthYear("01", "30")),
                    arrayOf("01/30", false, true, CardMonthYear("1", "2030")),
                    arrayOf("01/30", true, true, CardMonthYear("01", "2030"))
                )
            }
        }

        @Test
        fun from() {
            // when
            val actual = CardMonthYear.from(monthYear, monthZeroPadding, yearFullDigits)

            // then
            assertEquals(output.month, actual.month)
            assertEquals(output.year, actual.year)
        }
    }

    @RunWith(Parameterized::class)
    class Format(
        private val monthYear: CardMonthYear,
        private val output: String
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf(CardMonthYear("", ""), ""),
                    arrayOf(CardMonthYear("1", ""), "1/"),
                    arrayOf(CardMonthYear("01", ""), "01/"),
                    arrayOf(CardMonthYear("10", ""), "10/"),
                    arrayOf(CardMonthYear("", "30"), "/30"),
                    arrayOf(CardMonthYear("01", "30"), "01/30")
                )
            }
        }

        @Test
        fun from() {
            assertEquals(output, monthYear.formatText())
        }
    }
}