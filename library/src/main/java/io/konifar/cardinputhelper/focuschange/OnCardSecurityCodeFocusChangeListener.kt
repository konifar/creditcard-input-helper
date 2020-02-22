package io.konifar.cardinputhelper.focuschange

import android.view.View
import android.widget.EditText
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.validator.CardSecurityCodeValidator
import io.konifar.cardinputhelper.validator.errors.CardSecurityCodeError

open class OnCardSecurityCodeFocusChangeListener(
    private val cardNumberEditText: EditText,
    private val supportedCardBrand: Array<CardBrand>
) : View.OnFocusChangeListener {

    open fun onErrorDetected(error: CardSecurityCodeError) {}

    override fun onFocusChange(view: View?, focus: Boolean) {
        if (!focus) {
            val brand = CardBrand.from(cardNumberEditText.text, supportedCardBrand)
            val error = CardSecurityCodeValidator.validateOnFocusChanged((view as EditText).text, brand)
            onErrorDetected(error)
        }
    }
}