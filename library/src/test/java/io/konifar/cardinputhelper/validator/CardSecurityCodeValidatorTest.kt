package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.cardbrand.Amex
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.cardbrand.Visa
import io.konifar.cardinputhelper.validator.errors.CardSecurityCodeError
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CardSecurityCodeValidatorTest(
    private val code: CharSequence,
    private val cardBrand: CardBrand,
    private val output: CardSecurityCodeError
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<out Any?>> {
            return listOf(
                arrayOf("", Visa(), CardSecurityCodeError.EMPTY),
                arrayOf("1", Visa(), CardSecurityCodeError.NOT_ENOUGH_LENGTH),
                arrayOf("12", Visa(), CardSecurityCodeError.NOT_ENOUGH_LENGTH),
                arrayOf("123", Visa(), CardSecurityCodeError.NONE),
                arrayOf("", Amex(), CardSecurityCodeError.EMPTY),
                arrayOf("123", Amex(), CardSecurityCodeError.NOT_ENOUGH_LENGTH),
                arrayOf("1234", Amex(), CardSecurityCodeError.NONE)
            )
        }
    }

    @Test
    fun validateOnFocusChanged() {
        val actual = CardSecurityCodeValidator.validateOnFocusChanged(code, cardBrand)
        assertEquals(output, actual)
    }
}
