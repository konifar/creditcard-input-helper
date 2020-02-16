package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.cardbrand.Mastercard
import io.konifar.cardinputhelper.cardbrand.UnSupported
import io.konifar.cardinputhelper.cardbrand.Visa
import io.konifar.cardinputhelper.validator.errors.CardNumberError
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class CardNumberValidatorTest {

    @RunWith(Parameterized::class)
    class FocusChanged(
        private val cardNumber: CharSequence,
        private val cardBrand: CardBrand,
        private val output: CardNumberError
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", Visa(), CardNumberError.EMPTY),
                    arrayOf("424242424242424", Visa(), CardNumberError.NOT_ENOUGH_LENGTH),
                    arrayOf("4242424242424242", UnSupported(), CardNumberError.UNSUPPORTED_BRAND),
                    arrayOf("5555555555554444", Visa(), CardNumberError.INVALID_BRAND_FORMAT),
                    arrayOf("4242424242424243", Visa(), CardNumberError.INVALID_CARD_NUMBER),
                    arrayOf("4242424242424242", Visa(), CardNumberError.NONE),
                    arrayOf("5555555555554444", Mastercard(), CardNumberError.NONE)
                )
            }
        }

        @Test
        fun validateOnFocusChanged() {
            val actual = CardNumberValidator.validateOnFocusChanged(cardNumber, cardBrand)
            assertEquals(output, actual)
        }
    }

    @RunWith(Parameterized::class)
    class TextChanged(
        private val cardNumber: CharSequence,
        private val cardBrand: CardBrand,
        private val output: CardNumberError
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): List<Array<out Any?>> {
                return listOf(
                    arrayOf("", Visa(), CardNumberError.NONE),
                    arrayOf("424242424242424", Visa(), CardNumberError.NONE),
                    arrayOf("4242424242424242", UnSupported(), CardNumberError.UNSUPPORTED_BRAND),
                    arrayOf("5555555555554444", Visa(), CardNumberError.INVALID_BRAND_FORMAT),
                    arrayOf("4242424242424243", Visa(), CardNumberError.INVALID_CARD_NUMBER),
                    arrayOf("4242424242424242", Visa(), CardNumberError.NONE),
                    arrayOf("5555555555554444", Mastercard(), CardNumberError.NONE)
                )
            }
        }

        @Test
        fun validateOnTextChanged() {
            assertEquals(output, CardNumberValidator.validateOnTextChanged(cardNumber, cardBrand))
        }
    }
}