package io.konifar.cardinputhelper.validator

import io.konifar.cardinputhelper.ext.digits

/**
 * https://en.wikipedia.org/wiki/Luhn_algorithm
 */
object LuhnAlgorithm {
    fun isValid(input: CharSequence): Boolean {
        val sanitizedInput = input.digits()

        return when {
            valid(sanitizedInput) -> checkSum(sanitizedInput) % 10 == 0
            else -> false
        }
    }

    private fun valid(input: String) = input.all(Char::isDigit) && input.length > 1

    private fun checkSum(input: String) = addEnds(input).sum()

    private fun addEnds(input: String) = input.map(Character::getNumericValue).mapIndexed { i, j ->
        when {
            (input.length - i + 1) % 2 == 0 -> j
            j >= 5 -> j * 2 - 9
            else -> j * 2
        }
    }
}