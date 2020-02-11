package io.konifar.cardinputhelper

import android.os.Handler
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.formatter.CardNumberFormatter
import io.konifar.cardinputhelper.formatter.CardNumberSeparatorType
import io.konifar.cardinputhelper.validator.CardNumberValidator
import io.konifar.cardinputhelper.validator.errors.CardNumberError

open class CardNumberTextWatcher(
    private val separatorType: CardNumberSeparatorType = CardNumberSeparatorType.SPACE,
    private val supportedCardBrand: Array<CardBrand> = CardBrand.all,
    private val completedCheckDelayMills: Long = 1000L
) : TextWatcher {

    private var isChangingText = false
    private var cursorPos = 0
    private var editVelocity = 0

    private var currentCardBrand: CardBrand? = null

    private val handler = Handler()
    private val runnable = Runnable {
        currentCardBrand?.let {
            onCardNumberCompleted(it)
        }
    }

    open fun onCardBrandChanged(cardBrand: CardBrand) {}

    open fun onCardNumberErrorChanged(error: CardNumberError) {}

    open fun onCardNumberCompleted(cardBrand: CardBrand) {}

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
            checkEditCompleted(s)

            isChangingText = false
        }
    }

    private fun formatText(s: Editable) {
        currentCardBrand?.let {
            val formattedText = CardNumberFormatter.format(s, it, separatorType)
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

    private fun checkEditCompleted(s: Editable) {
        handler.removeCallbacks(runnable)
        currentCardBrand?.let {
            val error = CardNumberValidator.validateOnFocusChanged(s, it)
            if (error == CardNumberError.NONE) {
                handler.postDelayed(runnable, completedCheckDelayMills)
            }
        }
    }

    private fun adjustCursorPos(formattedText: String, s: Editable) {
        val i = cursorPos
        val formattedTextLength = formattedText.length
        if (cursorPos >= formattedTextLength) {
            cursorPos = formattedTextLength
        }
        if (editVelocity > 0 && cursorPos > 0 && formattedText[cursorPos - 1] == separatorType.character) {
            cursorPos += 1
        }
        if (editVelocity < 0 && cursorPos > 1 && formattedText[cursorPos - 1] == separatorType.character) {
            cursorPos -= 1
        }
        if (cursorPos != i) {
            Selection.setSelection(s, cursorPos)
        }
    }
}
