package io.konifar.cardinputhelper

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher

class CardNumber4digitsFormatTextWatcher : TextWatcher {
    private var current = ""

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (s.toString() != current) {
            val input = s.toString().replace(nonDigits, "")
            if (input.length <= 16) {
                current = input.chunked(4).joinToString(" ")
                s.filters = arrayOfNulls<InputFilter>(0)
            }
            s.replace(0, s.length, current, 0, current.length)
        }
    }

    companion object {
        private val nonDigits = Regex("[^\\d]")
    }
}