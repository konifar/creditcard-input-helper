package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.validator.errors.CardSecurityCodeError

object CardSecurityCodeValidator {

    fun validateOnFocusChanged(
        code: CharSequence,
        cardNumber: CharSequence,
        supportedCardBrand: Array<CardBrand>
    ): CardSecurityCodeError {
        val cardBrand = CardBrand.from(cardNumber, supportedCardBrand)
        return validateOnFocusChanged(code, cardBrand)
    }

    fun validateOnFocusChanged(code: CharSequence, cardBrand: CardBrand): CardSecurityCodeError {
        if (!validateEmpty(code.toString())) return CardSecurityCodeError.EMPTY
        if (!validateLength(code.toString(), cardBrand)) return CardSecurityCodeError.NOT_ENOUGH_LENGTH
        return CardSecurityCodeError.NONE
    }

    private fun validateLength(code: String, cardBrand: CardBrand) = cardBrand.hasEnoughSecurityCodeLength(code)

    private fun validateEmpty(code: String) = code.isNotEmpty()
}