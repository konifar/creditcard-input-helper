package io.konifar.cardinputhelper.formatter

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CardMonthYearFormatterTest {

    @RunWith(Parameterized::class)
    class ForInsert(
        private val input: String,
        private val output: String
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    // Slash is not included
                    arrayOf("", ""),
                    arrayOf("0", "0/"),
                    arrayOf("1", "1/"),
                    arrayOf("2", "2/"),
                    arrayOf("9", "9/"),
                    arrayOf("10", "10/"),
                    arrayOf("12", "12/"),
                    arrayOf("01", "01/"),
                    arrayOf("13", "1/3"),
                    arrayOf("13/", "1/3"),
                    arrayOf("012", "01/2"),
                    arrayOf("123", "12/3"),
                    arrayOf("234", "2/34"),
                    arrayOf("1234", "12/34"),
                    arrayOf("2345", "2/34"),
                    // Month is empty
                    arrayOf("/", ""),
                    arrayOf("/1/", "/1"),
                    arrayOf("/0", "/0"),
                    arrayOf("/1", "/1"),
                    arrayOf("/01", "/01"),
                    arrayOf("/12", "/12"),
                    arrayOf("/123", "/12"),
                    // Month length is 1
                    arrayOf("0/", "0/"),
                    arrayOf("1/", "1/"),
                    arrayOf("1//", "1/"),
                    arrayOf("1/2", "1/2"),
                    arrayOf("1/23", "1/23"),
                    arrayOf("1/234", "1/23"),
                    // Month length is 2
                    arrayOf("01/", "01/"),
                    arrayOf("01/2", "01/2"),
                    arrayOf("12/3", "12/3"),
                    arrayOf("13/3", "1/3"),
                    arrayOf("01/23", "01/23"),
                    arrayOf("12/34", "12/34"),
                    arrayOf("13/45", "1/45"),
                    // Month length is 3
                    arrayOf("123/", "12/3"),
                    arrayOf("001/", "00/1"),
                    arrayOf("012/", "01/2"),
                    arrayOf("013/", "01/3"),
                    arrayOf("123/4", "12/34"),
                    arrayOf("134/", "1/"),
                    arrayOf("134/5", "1/5"),
                    // Month length is 4
                    arrayOf("1234/", "12/34"),
                    arrayOf("1345/", "1/"),
                    arrayOf("0101/", "01/01"),
                    arrayOf("1234/5", "12/34"),
                    arrayOf("1345/5", "1/5")
                )
            }
        }

        @Test
        fun formatForInsert() {
            assertEquals(output, CardMonthYearFormatter.formatForInsert(input))
        }
    }

    @RunWith(Parameterized::class)
    class ForDelete(
        private val inputAfter: String,
        private val inputBefore: String,
        private val output: String
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", "/", ""),
                    arrayOf("/", "/1", ""),
                    arrayOf("/", "1/", ""),
                    arrayOf("1", "1/", ""),
                    arrayOf("/1", "/12", "/1"),
                    arrayOf("12", "/12", "/12"),
                    arrayOf("/2", "/12", "/2"),
                    arrayOf("1/", "1/2", "1/"),
                    arrayOf("12", "1/2", "/2"),
                    arrayOf("/2", "1/2", "/2"),
                    arrayOf("01/", "01/2", "01/"),
                    arrayOf("0/2", "01/2", "0/2"),
                    arrayOf("1/2", "01/2", "1/2"),
                    arrayOf("012", "01/2", "0/2"),
                    arrayOf("01/", "01/2", "01/")
                )
            }
        }

        @Test
        fun formatForDelete() {
            assertEquals(output, CardMonthYearFormatter.formatForDelete(inputAfter, inputBefore))
        }
    }

    @RunWith(Parameterized::class)
    class CursorPos(
        private val inputFormatted: String,
        private val inputAfter: String,
        private val inputBefore: String,
        private val output: Int
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    // Inserted slash
                    arrayOf("0/", "0", "", 1),
                    arrayOf("1/", "1", "", 1),
                    arrayOf("2/", "2", "", 2),
                    // Inserted month
                    arrayOf("12/", "12/", "1/", 3),
                    arrayOf("1/3", "13/", "1/", 3),
                    arrayOf("1/", "1//", "1/", 2),
                    arrayOf("1/", "/1/", "1/", 0),
                    arrayOf("1/2", "1/2", "/2", 1),
                    arrayOf("12/34", "12/34", "2/34", 1),
                    // Inserted year
                    arrayOf("12/3", "12/3", "12/", 4),
                    arrayOf("12/3", "123/", "12/", 4),
                    arrayOf("01/2", "01/2", "01/", 4),
                    arrayOf("01/23", "01/23", "01/2", 5),
                    arrayOf("01/32", "01/32", "01/2", 4),
                    arrayOf("12/3", "12/3", "1/3", 2),
                    arrayOf("12/34", "12/34", "12/345", 5),
                    arrayOf("1/23", "1/23", "1/234", 4),
                    // Deleted slash
                    arrayOf("", "", "/", 0),
                    arrayOf("", "", "0", 0),
                    arrayOf("", "1", "1/", 0),
                    arrayOf("", "/", "1/", 0),
                    // Deleted month
                    arrayOf("1/", "12", "12/", 1),
                    arrayOf("0/", "01", "01/", 1),
                    arrayOf("1/", "1/", "12/", 1),
                    arrayOf("2/", "2/", "12/", 0),
                    arrayOf("1/3", "1/3", "12/3", 1),
                    arrayOf("1/3", "1/3", "01/3", 0),
                    arrayOf("2/3", "2/3", "12/3", 0),
                    arrayOf("01/", "01/", "01/3", 3),
                    arrayOf("01/3", "01/3", "01/23", 3),
                    arrayOf("01/2", "01/2", "01/23", 4),
                    arrayOf("1/3", "123", "12/3", 1)
                )
            }
        }

        @Test
        fun calculateCursorPos() {
            assertEquals(output, CardMonthYearFormatter.calculateCursorPos(inputFormatted, inputAfter, inputBefore))
        }
    }
}