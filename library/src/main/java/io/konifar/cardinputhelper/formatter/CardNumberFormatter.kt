package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.cardtype.CardType

class CardNumberFormatter {

    fun format(cardNumber: CharSequence, cardType: CardType, dividerType: DividerType): String {
        if (!cardType.validateFormatSetting()) {
            return cardNumber.toString()
        }
        val number = CardType.removeExceptDigit(cardNumber)
        val cardLength = cardType.length()
        if (cardLength <= 0) {
            return cardNumber.toString()
        }
        val formatLength = if (number.length > cardLength) cardLength else number.length

        return StringBuilder().apply {
            var start = 0
            var end = 0
            for (f in cardType.format) {
                end += f
                val isEnd = end >= formatLength
                append(number, start, if (isEnd) formatLength else end)
                if (!isEnd) {
                    append(dividerType.character)
                    start += f
                } else {
                    break
                }
            }
        }.toString()
    }

}