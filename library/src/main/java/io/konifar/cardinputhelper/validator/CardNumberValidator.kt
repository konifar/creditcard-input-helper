package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.cardbrand.UnSupported
import io.konifar.cardinputhelper.ext.digits
import io.konifar.cardinputhelper.validator.errors.CardNumberError

object CardNumberValidator {

    fun validateOnFocusChanged(cardNumber: CharSequence, supportedCardBrand: Array<CardBrand>): CardNumberError {
        val cardBrand = CardBrand.from(cardNumber, supportedCardBrand)
        return validateOnFocusChanged(cardNumber, cardBrand)
    }

    fun validateOnFocusChanged(cardNumber: CharSequence, cardBrand: CardBrand): CardNumberError {
        val number = cardNumber.digits()
        if (!validateEmpty(number)) return CardNumberError.IS_EMPTY
        if (!validateLength(number, cardBrand)) return CardNumberError.NOT_ENOUGH_LENGTH
        if (!validateSupportedBrand(cardBrand)) return CardNumberError.UNSUPPORTED_BRAND
        if (!validateBrandFormat(number, cardBrand)) return CardNumberError.INVALID_BRAND_FORMAT
        if (!validateLuhnAlgorithm(number)) return CardNumberError.INVALID_CARD_NUMBER
        return CardNumberError.NONE
    }

    fun validateOnTextChanged(cardNumber: CharSequence, cardBrand: CardBrand): CardNumberError {
        val number = cardNumber.digits()
        if (cardBrand.hasEnoughNumberLength(cardNumber)) {
            if (!validateSupportedBrand(cardBrand)) return CardNumberError.UNSUPPORTED_BRAND
            if (!validateBrandFormat(number, cardBrand)) return CardNumberError.INVALID_BRAND_FORMAT
            if (!validateLuhnAlgorithm(number)) return CardNumberError.INVALID_CARD_NUMBER
        }
        return CardNumberError.NONE
    }

    private fun validateLuhnAlgorithm(number: String) = LuhnAlgorithm.isValid(number)

    private fun validateLength(number: String, cardBrand: CardBrand) = cardBrand.hasEnoughNumberLength(number)

    private fun validateEmpty(number: String) = number.isNotEmpty()

    private fun validateSupportedBrand(cardBrand: CardBrand) = cardBrand !is UnSupported

    private fun validateBrandFormat(number: String, cardBrand: CardBrand) = cardBrand.isValidFormat(number)
}