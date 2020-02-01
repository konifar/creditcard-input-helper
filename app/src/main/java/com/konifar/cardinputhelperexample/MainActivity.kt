package com.konifar.cardinputhelperexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.konifar.cardinputhelper.R
import com.konifar.cardinputhelper.databinding.ActivityMainBinding
import io.konifar.cardinputhelper.cardtype.*
import io.konifar.cardinputhelper.formatter.CardNumberFormatTextWatcher

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.panEdit.addTextChangedListener(object : CardNumberFormatTextWatcher() {
            override fun onCardTypeChanged(cardType: CardType) {
                bindBrandName(cardType)
            }
        })
    }

    private fun bindBrandName(cardType: CardType) {
        val brandName = when (cardType) {
            is AmexCardType -> "American Express"
            is DinersCardType -> "Diners Club"
            is DiscoverCardType -> "Discover"
            is JcbCardType -> "JCB"
            is MastercardCardType -> "Mastercard"
            is VisaCardType -> "Visa"
            else -> "" // Unknown
        }
        binding.brandName.text = brandName
    }
}
