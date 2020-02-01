package io.konifar.cardinputhelper

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.formatter.CardNumberFormatter
import io.konifar.cardinputhelper.formatter.DividerType
import io.konifar.cardinputhelper.validator.errors.CardNumberError
import io.konifar.cardinputhelper.validator.CardNumberValidator

open class CardNumberTextWatcher(
    private val dividerType: DividerType = DividerType.SPACE,
    private val supportedCardBrand: Array<CardBrand> = CardBrand.all
) : TextWatcher {

    private var isChangingText = false
    private var cursorPos = 0
    private var editVelocity = 0

    private var currentCardBrand: CardBrand? = null

    open fun onCardBrandChanged(cardBrand: CardBrand) {}

    open fun onCardNumberErrorChanged(error: CardNumberError) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!isChangingText) {
            editVelocity = count - before
            cursorPos = start + count
        }
    }

    override fun afterTextChanged(s: Editable) {
        if (!isChangingText) {
            isChangingText = true

            val nextCardBrand = CardBrand.from(s, supportedCardBrand)
            if (currentCardBrand != nextCardBrand) {
                currentCardBrand = nextCardBrand
                currentCardBrand?.let {
                    onCardBrandChanged(it)
                }
            }

            formatText(s)
            validateText(s)

            isChangingText = false
        }
    }

    private fun formatText(s: Editable) {
        currentCardBrand?.let {
            val formattedText = CardNumberFormatter.format(s, it, dividerType)
            s.replace(0, s.length, formattedText)
            adjustCursorPos(formattedText, s)
        }
    }

    private fun validateText(s: Editable) {
        currentCardBrand?.let {
            val error = CardNumberValidator.validateOnTextChanged(s, it)
            onCardNumberErrorChanged(error)
        }
    }

    private fun adjustCursorPos(formattedText: String, s: Editable) {
        val i = cursorPos
        val formattedTextLength = formattedText.length
        if (cursorPos >= formattedTextLength) {
            cursorPos = formattedTextLength
        }
        if (editVelocity > 0 && cursorPos > 0 && formattedText[cursorPos - 1] == dividerType.character) {
            cursorPos += 1
        }
        if (editVelocity < 0 && cursorPos > 1 && formattedText[cursorPos - 1] == dividerType.character) {
            cursorPos -= 1
        }
        if (cursorPos != i) {
            Selection.setSelection(s, cursorPos)
        }
    }
}
