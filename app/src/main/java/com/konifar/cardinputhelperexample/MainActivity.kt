package com.konifar.cardinputhelperexample

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.konifar.cardinputhelper.R
import com.konifar.cardinputhelper.databinding.MainActivityBinding
import io.konifar.cardinputhelper.CardMonthYearTextWatcher
import io.konifar.cardinputhelper.CardNumberTextWatcher
import io.konifar.cardinputhelper.NoCopyAndCutSelectionActionModeCallback
import io.konifar.cardinputhelper.cardbrand.*
import io.konifar.cardinputhelper.ext.digits
import io.konifar.cardinputhelper.focuschange.OnCardMonthYearFocusChangeListener
import io.konifar.cardinputhelper.focuschange.OnCardNumberFocusChangeListener
import io.konifar.cardinputhelper.focuschange.OnCardSecurityCodeFocusChangeListener
import io.konifar.cardinputhelper.formatter.CardMonthYearFormatter
import io.konifar.cardinputhelper.formatter.CardNumberSeparatorType
import io.konifar.cardinputhelper.validator.CardMonthYearValidator
import io.konifar.cardinputhelper.validator.CardNumberValidator
import io.konifar.cardinputhelper.validator.CardSecurityCodeValidator
import io.konifar.cardinputhelper.validator.errors.CardMonthYearError
import io.konifar.cardinputhelper.validator.errors.CardNumberError
import io.konifar.cardinputhelper.validator.errors.CardSecurityCodeError


class MainActivity : AppCompatActivity() {

    companion object {
        val SUPPORTED_CARD_BRANDS = arrayOf(
            Visa(),
            Amex(),
            Mastercard()
        )
    }

    private val binding: MainActivityBinding by lazy {
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.panEdit.apply {
            addTextChangedListener(object : CardNumberTextWatcher(
                separatorType = CardNumberSeparatorType.HYPHEN,
                supportedCardBrand = SUPPORTED_CARD_BRANDS
            ) {
                override fun onCardBrandChanged(cardBrand: CardBrand) {
                    bindBrandName(cardBrand)
                    clearCvv2(cardBrand)
                }

                override fun onCardNumberErrorChanged(error: CardNumberError) = bindNumberError(error)

                override fun onCardNumberCompleted(cardBrand: CardBrand) {
                    binding.expiryMonthYearEdit.requestFocus()
                }
            })
            onFocusChangeListener = object : OnCardNumberFocusChangeListener(SUPPORTED_CARD_BRANDS) {
                override fun onErrorDetected(error: CardNumberError) = bindNumberError(error)
            }

            customSelectionActionModeCallback = NoCopyAndCutSelectionActionModeCallback()
        }

        binding.expiryMonthYearEdit.apply {
            addTextChangedListener(object : CardMonthYearTextWatcher() {
                override fun onCardMonthYearErrorChanged(error: CardMonthYearError) = bindMonthYearError(error)

                override fun onCardMonthYearCompleted() {
                    binding.cvv2Edit.requestFocus()
                }
            })
            onFocusChangeListener = object : OnCardMonthYearFocusChangeListener(true) {
                override fun onErrorDetected(error: CardMonthYearError) = bindMonthYearError(error)
            }
        }

        binding.cvv2Edit.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) = bindSecurityCodeError(CardSecurityCodeError.NONE)

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })
            onFocusChangeListener =
                object : OnCardSecurityCodeFocusChangeListener(binding.panEdit, SUPPORTED_CARD_BRANDS) {
                    override fun onErrorDetected(error: CardSecurityCodeError) = bindSecurityCodeError(error)
                }
        }

        binding.check.setOnClickListener {
            validateNumber()
            validateMonthYear()
            validateSecurityCode()
            val hasError = focusErrorEditText()
            if (!hasError) {
                showSuccessToast()
            }
        }
    }

    private fun showSuccessToast() {
        val number = binding.panEdit.text
        val month = CardMonthYearFormatter.extractMonth(binding.expiryMonthYearEdit.text.toString(), true)
        val year = CardMonthYearFormatter.extractYear(binding.expiryMonthYearEdit.text.toString(), true)
        val securityCode = binding.cvv2Edit.text
        val text = "Number: $number\nMonth: $month\nYear: $year\nSecurityCode: $securityCode"
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun clearCvv2(cardBrand: CardBrand) {
        binding.cvv2Edit.run {
            text = null
            filters = arrayOf(LengthFilter(cardBrand.securityCodeLength))
        }
        binding.cvv2.error = null
    }

    private fun validateSecurityCode() {
        val cvv2 = binding.cvv2Edit.text
        val number = binding.panEdit.text.digits()
        val error = CardSecurityCodeValidator.validateOnFocusChanged(cvv2, number, SUPPORTED_CARD_BRANDS)
        bindSecurityCodeError(error)
    }

    private fun validateMonthYear() {
        val error = CardMonthYearValidator.validateOnFocusChanged(binding.expiryMonthYearEdit.text)
        bindMonthYearError(error)
    }

    private fun validateNumber() {
        val number = binding.panEdit.text
        val error = CardNumberValidator.validateOnFocusChanged(number, SUPPORTED_CARD_BRANDS)
        bindNumberError(error)
    }

    private fun bindNumberError(error: CardNumberError) {
        val errorResId = when (error) {
            CardNumberError.INVALID_BRAND_FORMAT -> R.string.number_error_invalid_brand_format
            CardNumberError.INVALID_CARD_NUMBER -> R.string.number_error_invalid_card_number
            CardNumberError.EMPTY -> R.string.number_error_empty
            CardNumberError.UNSUPPORTED_BRAND -> R.string.number_error_unsupported_brand
            CardNumberError.NOT_ENOUGH_LENGTH -> R.string.number_error_not_enough_length
            else -> 0
        }

        if (errorResId > 0) {
            binding.pan.error = getString(errorResId)
        } else {
            binding.pan.error = null
        }
    }

    private fun bindMonthYearError(error: CardMonthYearError) {
        val errorResId = when (error) {
            CardMonthYearError.EXPIRED -> R.string.month_year_error_expired
            CardMonthYearError.YEAR_INVALID -> R.string.month_year_error_year_invalid
            CardMonthYearError.YEAR_REQUIRED -> R.string.month_year_error_year_required
            CardMonthYearError.MONTH_INVALID -> R.string.month_year_error_month_invalid
            CardMonthYearError.MONTH_REQUIRED -> R.string.month_year_error_month_required
            CardMonthYearError.EMPTY -> R.string.month_year_error_empty
            else -> 0
        }

        if (errorResId > 0) {
            binding.expiryMonthYear.error = getString(errorResId)
        } else {
            binding.expiryMonthYear.error = null
        }
    }

    private fun bindSecurityCodeError(error: CardSecurityCodeError) {
        val errorResId = when (error) {
            CardSecurityCodeError.EMPTY -> R.string.cvv2_error_empty
            CardSecurityCodeError.NOT_ENOUGH_LENGTH -> R.string.cvv2_error_not_enough_length
            else -> 0
        }

        if (errorResId > 0) {
            binding.cvv2.error = getString(errorResId)
        } else {
            binding.cvv2.error = null
        }
    }

    private fun bindBrandName(cardType: CardBrand) {
        val brandName = when (cardType) {
            is Amex -> "American Express"
            is Diners -> "Diners Club"
            is Discover -> "Discover"
            is Jcb -> "JCB"
            is Mastercard -> "Mastercard"
            is Visa -> "Visa"
            is Unchecked -> ""
            else -> "Unsupported"
        }
        binding.brandName.text = brandName
    }

    private fun focusErrorEditText(): Boolean {
        if (binding.pan.error != null) {
            binding.pan.editText?.requestFocus()
            return true
        }
        if (binding.expiryMonthYear.error != null) {
            binding.expiryMonthYear.editText?.requestFocus()
            return true
        }
        if (binding.cvv2.error != null) {
            binding.cvv2.editText?.requestFocus()
            return true
        }

        return false
    }
}
