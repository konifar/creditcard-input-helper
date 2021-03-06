package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.cardbrand.CardBrand
import io.konifar.cardinputhelper.ext.digits

object CardNumberFormatter {

    fun format(cardNumber: CharSequence, cardBrand: CardBrand, separatorType: CardNumberSeparatorType): String {
        if (!cardBrand.validateFormatSetting()) {
            return cardNumber.toString()
        }
        val number = cardNumber.digits()
        val cardLength = cardBrand.length()
        if (cardLength <= 0) {
            return cardNumber.toString()
        }
        val formatLength = if (number.length > cardLength) cardLength else number.length

        return StringBuilder().apply {
            var start = 0
            var end = 0
            for (f in cardBrand.format) {
                end += f
                val isEnd = end >= formatLength
                append(number, start, if (isEnd) formatLength else end)
                if (!isEnd) {
                    append(separatorType.character)
                    start += f
                } else {
                    break
                }
            }
        }.toString()
    }

}