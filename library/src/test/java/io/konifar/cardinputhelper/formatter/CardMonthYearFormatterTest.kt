package io.konifar.cardinputhelper.formatter

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CardMonthYearFormatterTest(
    private val input: String,
    private val output: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<out Any?>> {
            return listOf(
                arrayOf("", ""),
                arrayOf("0", "0"),
                arrayOf("1", "1"),
                arrayOf("2", "02/"),
                arrayOf("3", "03/"),
                arrayOf("9", "09/"),
                arrayOf("10", "10/"),
                arrayOf("12", "12/"),
                arrayOf("13", "01/3"),
                arrayOf("13", "01/3"),
                arrayOf("/", ""),
                arrayOf("01/", "01/"),
                arrayOf("01/2", "01/2"),
                arrayOf("01/23", "01/23"),
                arrayOf("123/", "12/3"),
                arrayOf("1/", "1/"),
                arrayOf("1/2", "1/2"),
                arrayOf("0/2", "0/2"),
                arrayOf("1/23", "1/23"),
                arrayOf("1/234", "1/23"),
                arrayOf("12/34", "12/34"),
                arrayOf("123/4", "12/34"),
                arrayOf("1234/", "12/34"),
                arrayOf("/1", "/1"),
                arrayOf("/12", "/12"),
                arrayOf("/123", "/12")
            )
        }
    }

    @Test
    fun format() {
        assertEquals(output, CardMonthYearFormatter.format(input))
    }
}