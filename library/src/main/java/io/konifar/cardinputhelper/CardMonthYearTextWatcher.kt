package io.konifar.cardinputhelper

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter
import io.konifar.cardinputhelper.validator.CardMonthYearValidator
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError

open class CardMonthYearTextWatcher : TextWatcher {

    private var isChangingText = false
    private var cursorPos = 0
    private var editVelocity = 0
    private var beforeCharsCount = 0

    open fun onCardMonthYearErrorChanged(error: CardMonthYearError) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!isChangingText) {
            beforeCharsCount = s.length
            editVelocity = count - before
            cursorPos = start + count
        }
    }

    override fun afterTextChanged(s: Editable) {
        if (!isChangingText) {
            isChangingText = true

            val isDeleting = s.length < beforeCharsCount
            if (isDeleting) {
                
            }

            val formattedText = CardMonthYearFormatter.format(s)
            s.replace(0, s.length, formattedText)

            val error = CardMonthYearValidator.validateOnTextChanged(formattedText)
            onCardMonthYearErrorChanged(error)

            adjustCursorPos(formattedText, s)

            isChangingText = false
        }
    }

    private fun adjustCursorPos(formattedText: String, s: Editable) {
        val i = cursorPos
        val formattedTextLength = formattedText.length
        if (cursorPos >= formattedTextLength) {
            cursorPos = formattedTextLength
        }
        if (editVelocity > 0 && cursorPos > 0 && formattedText[cursorPos - 1] == CardMonthYearFormatter.SLASH.toCharArray().first()) {
            cursorPos += 1
        }
        if (editVelocity < 0 && cursorPos > 1 && formattedText[cursorPos - 1] == CardMonthYearFormatter.SLASH.toCharArray().first()) {
            cursorPos -= 1
        }
        if (cursorPos != i) {
            Selection.setSelection(s, cursorPos)
        }
    }
}