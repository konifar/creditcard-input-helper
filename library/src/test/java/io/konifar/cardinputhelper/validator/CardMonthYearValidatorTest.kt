package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.validator.errors.CardMonthYearError
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CardMonthYearValidatorTest {

    @RunWith(Parameterized::class)
    class FocusChanged(
        private val input: String,
        private val output: CardMonthYearError
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", CardMonthYearError.IS_EMPTY),
                    arrayOf("/", CardMonthYearError.MONTH_REQUIRED),
                    arrayOf("/1", CardMonthYearError.MONTH_REQUIRED),
                    arrayOf("0/35", CardMonthYearError.MONTH_INVALID),
                    arrayOf("13/1", CardMonthYearError.MONTH_INVALID),
                    arrayOf("1/", CardMonthYearError.YEAR_REQUIRED),
                    arrayOf("12/", CardMonthYearError.YEAR_REQUIRED),
                    arrayOf("12/36", CardMonthYearError.NONE)
                )
            }
        }

        @Test
        fun validateOnFocusChanged() {
            assertEquals(output, CardMonthYearValidator.validateOnFocusChanged(input))
        }
    }

    @RunWith(Parameterized::class)
    class TextChanged(
        private val input: String,
        private val output: CardMonthYearError
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", CardMonthYearError.NONE),
                    arrayOf("/", CardMonthYearError.NONE),
                    arrayOf("/1", CardMonthYearError.NONE),
                    arrayOf("0/35", CardMonthYearError.MONTH_INVALID),
                    arrayOf("13/1", CardMonthYearError.NONE),
                    arrayOf("13/35", CardMonthYearError.MONTH_INVALID),
                    arrayOf("1/", CardMonthYearError.NONE),
                    arrayOf("12/", CardMonthYearError.NONE),
                    arrayOf("12/36", CardMonthYearError.NONE)
                )
            }
        }

        @Test
        fun validateOnTextChanged() {
            assertEquals(output, CardMonthYearValidator.validateOnTextChanged(input))
        }
    }
}