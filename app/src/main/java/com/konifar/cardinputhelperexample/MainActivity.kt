package com.konifar.cardinputhelperexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.konifar.cardinputhelper.R
import com.konifar.cardinputhelper.databinding.ActivityMainBinding
import io.konifar.cardinputhelper.CardNumberTextWatcher
import io.konifar.cardinputhelper.cardbrand.*
import io.konifar.cardinputhelper.formatter.DividerType
import io.konifar.cardinputhelper.validator.CardNumberError
import io.konifar.cardinputhelper.validator.CardNumberValidator

class MainActivity : AppCompatActivity() {

    companion object {
        val SUPPORTED_CARD_BRANDS = arrayOf(
            Visa(),
            Amex(),
            Mastercard()
        )
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.panEdit.addTextChangedListener(object : CardNumberTextWatcher(
            dividerType = DividerType.HYPHEN,
            supportedCardBrand = SUPPORTED_CARD_BRANDS
        ) {
            override fun onCardBrandChanged(cardBrand: CardBrand) = bindBrandName(cardBrand)

            override fun onCardNumberErrorDetected(error: CardNumberError) = bindError(error)
        })

        binding.check.setOnClickListener {
            val number = binding.panEdit.text
            val error = CardNumberValidator.validate(number, SUPPORTED_CARD_BRANDS)
            bindError(error)
        }
    }

    private fun bindError(error: CardNumberError) {
        val errorResId = when (error) {
            CardNumberError.INVALID_BRAND_FORMAT -> R.string.number_error_invalid_brand_format
            CardNumberError.INVALID_CARD_NUMBER -> R.string.number_error_invalid_card_number
            CardNumberError.IS_EMPTY -> R.string.number_error_is_empty
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
}
