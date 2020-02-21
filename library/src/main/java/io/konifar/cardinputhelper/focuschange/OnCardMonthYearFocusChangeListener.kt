package io.konifar.cardinputhelper.focuschange

import android.view.View
import android.widget.EditText
import io.konifar.cardinputhelper.validator.CardMonthYearValidator
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError

open class OnCardMonthYearFocusChangeListener(
    private val needMonthZeroPadding: Boolean = false
) : View.OnFocusChangeListener {

    open fun onErrorDetected(error: CardMonthYearError) {}

    override fun onFocusChange(view: View?, focus: Boolean) {
        if (!focus) {
            val error = CardMonthYearValidator.validateOnFocusChanged((view as EditText).text)
            onErrorDetected(error)
        }
    }
}