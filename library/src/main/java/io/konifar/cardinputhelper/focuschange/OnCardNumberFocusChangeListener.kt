package io.konifar.cardinputhelper.focuschange

import android.view.View
import android.widget.EditText
import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.validator.CardNumberValidator
import io.konifar.cardinputhelper.validator.errors.CardNumberError

open class OnCardNumberFocusChangeListener(
    private val supportedCardBrand: Array<CardBrand>
) : View.OnFocusChangeListener {

    open fun onErrorDetected(error: CardNumberError) {}

    override fun onFocusChange(view: View?, focus: Boolean) {
        if (!focus) {
            val brand = CardBrand.from((view as EditText).text, supportedCardBrand)
            val error = CardNumberValidator.validateOnFocusChanged(view.text, brand)
            onErrorDetected(error)
        }
    }
}