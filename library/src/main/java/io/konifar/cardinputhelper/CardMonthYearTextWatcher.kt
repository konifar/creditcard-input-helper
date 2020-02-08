package io.konifar.cardinputhelper

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter
import io.konifar.cardinputhelper.validator.CardMonthYearValidator
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError

open class CardMonthYearTextWatcher : TextWatcher {

    private var isChangingText = false
    private var beforeText = ""
    private var oldBeforeText = ""

    open fun onCardMonthYearErrorChanged(error: CardMonthYearError) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        oldBeforeText = beforeText
        beforeText = s.toString()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (!isChangingText) {
            isChangingText = true

            val formattedText = if (isDeleted(s)) {
                CardMonthYearFormatter.formatForDeleting(s, beforeText)
            } else {
                CardMonthYearFormatter.formatForInserting(s)
            }

            s.replace(0, s.length, formattedText)
            val error = CardMonthYearValidator.validateOnTextChanged(formattedText)
            onCardMonthYearErrorChanged(error)

            val cursorPos = CardMonthYearFormatter.calculateCursorPos(formattedText, beforeText, oldBeforeText)
            Selection.setSelection(s, cursorPos)

            isChangingText = false
        }
    }

    private fun isDeleted(s: CharSequence) = s.length < beforeText.length
}