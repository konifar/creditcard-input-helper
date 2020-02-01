package com.konifar.cardinputhelperexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.konifar.cardinputhelper.R
import com.konifar.cardinputhelper.databinding.ActivityMainBinding
import io.konifar.cardinputhelper.cardbrand.*
import io.konifar.cardinputhelper.formatter.CardNumberFormatTextWatcher
import io.konifar.cardinputhelper.formatter.DividerType

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.panEdit.addTextChangedListener(object : CardNumberFormatTextWatcher(
            dividerType = DividerType.HYPHEN,
            supportedCardBrand = arrayOf(
                Visa(),
                Amex(),
                Mastercard()
            )
        ) {
            override fun onCardBrandChanged(cardBrand: CardBrand) {
                bindBrandName(cardBrand)
            }
        })
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
