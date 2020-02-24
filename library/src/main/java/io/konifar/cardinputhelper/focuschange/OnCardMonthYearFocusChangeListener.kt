package io.konifar.cardinputhelper.focuschange

import android.annotation.SuppressLint
import android.view.View
import android.widget.EditText
import io.konifar.cardinputhelper.CardMonthYear
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter.SLASH
import io.konifar.cardinputhelper.validator.CardMonthYearValidator
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError

open class OnCardMonthYearFocusChangeListener(
    private val needMonthZeroPadding: Boolean = false
) : View.OnFocusChangeListener {

    open fun onErrorDetected(error: CardMonthYearError) {}

    override fun onFocusChange(view: View?, focus: Boolean) {
        if (!focus) {
            if (needMonthZeroPadding) {
                insertMonthZeroIfNeed((view as EditText))
            }

            val error = CardMonthYearValidator.validateOnFocusChanged((view as EditText).text)
            onErrorDetected(error)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun insertMonthZeroIfNeed(editText: EditText) {
        val text = editText.text.toString()
        val monthYear = CardMonthYear.from(text, true, false)
        editText.setText("${monthYear.month}$SLASH${monthYear.year}")
    }
}