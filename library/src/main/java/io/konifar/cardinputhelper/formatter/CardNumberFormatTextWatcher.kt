package io.konifar.cardinputhelper.formatter

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.konifar.cardinputhelper.cardtype.CardType

open class CardNumberFormatTextWatcher(
    private val dividerType: DividerType = DividerType.SPACE,
    private val supportedCardType: Array<CardType> = CardType.all
) : TextWatcher {

    private val formatter = CardNumberFormatter()
    private var isChangingText = false
    private var cursorPos = 0
    private var editVelocity = 0

    private var currentCardType: CardType? = null

    open fun onCardTypeChanged(cardType: CardType) {}

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

            val nextCardType = CardType.from(s, supportedCardType)
            if (currentCardType != nextCardType) {
                currentCardType = nextCardType
                currentCardType?.let {
                    onCardTypeChanged(it)
                }
            }

            formatText(s)
            isChangingText = false
        }
    }

    private fun formatText(s: Editable) {
        currentCardType?.let {
            val formattedText = formatter.format(s, it, dividerType)
            s.replace(0, s.length, formattedText)
            adjustCursorPos(formattedText, s)
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
