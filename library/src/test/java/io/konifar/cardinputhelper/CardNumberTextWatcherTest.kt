package io.konifar.cardinputhelper

import android.text.Editable
import android.text.SpannableStringBuilder
import io.konifar.cardinputhelper.cardbrand.Amex
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.cardbrand.Unchecked
import io.konifar.cardinputhelper.cardbrand.Visa
import io.konifar.cardinputhelper.validator.errors.CardNumberError
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CardNumberTextWatcherTest(

) {
    @Test
    fun insertDigits() {
        // given
        var brand: CardBrand? = null
        var cardNumberError: CardNumberError? = null
        val watcher = object : CardNumberTextWatcher(
            completedCheckDelayMills = 0
        ) {
            override fun onCardBrandChanged(cardBrand: CardBrand) {
                brand = cardBrand
            }

            override fun onCardNumberErrorChanged(error: CardNumberError) {
                cardNumberError = error
            }
        }

        arrayOf(
            InsertData(
                "",
                "4",
                "4",
                Unchecked(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "4242",
                "4242",
                Visa(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "42424",
                "4242 4",
                Visa(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "4242 42424",
                "4242 4242 4",
                Visa(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "4242424242424242",
                "4242 4242 4242 4242",
                Visa(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "4242424242424243",
                "4242 4242 4242 4243",
                Visa(),
                CardNumberError.INVALID_CARD_NUMBER
            ),
            InsertData(
                "4242",
                "4",
                "4242 4",
                Visa(),
                CardNumberError.NONE
            ),
            InsertData(
                "",
                "378282246310005",
                "3782 822463 10005",
                Amex(),
                CardNumberError.NONE
            )
        ).forEach {
            // when
            val editable = watcher.insertText(it.original, it.input)

            // then
            assertEquals(it.output, editable.toString())
            assertEquals(brand?.javaClass?.name, it.cardBrand?.javaClass?.name)
            assertEquals(cardNumberError?.javaClass?.name, it.error?.javaClass?.name)
        }
    }

    private fun CardNumberTextWatcher.insertText(original: String, input: String): Editable {
        beforeTextChanged(original, 0, original.length, input.length)
        val newText = original + input
        onTextChanged(newText, 1, newText.length - 1, 1)
        val editable = SpannableStringBuilder(newText)
        afterTextChanged(editable)
        return editable
    }

    private class InsertData(
        val original: String,
        val input: String,
        val output: String,
        val cardBrand: CardBrand?,
        val error: CardNumberError?
    )
}