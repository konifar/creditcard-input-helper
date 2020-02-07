package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.ext.digitsAndSlash

object CardMonthYearFormatter {
    const val SLASH = "/"

    private fun removeUnnecessarySlash(text: String): String {
        val firstSlashIndex = text.indexOf(SLASH)
        val lastSlashIndex = text.lastIndexOf(SLASH)
        if (firstSlashIndex >= 0 && lastSlashIndex >= 0 && firstSlashIndex != lastSlashIndex) {
            val modified = text.replace(SLASH, "")
            return StringBuilder(modified).insert(firstSlashIndex, SLASH).toString()
        }
        return text
    }

    fun formatForPasting(monthYear: CharSequence): String {
        return monthYear.toString()
    }

    fun formatForInsertingChar(monthYear: CharSequence): String {
        val checkedMonthYear = removeUnnecessarySlash(monthYear.digitsAndSlash())
        if (checkedMonthYear.isEmpty()) {
            return ""
        }

        val monthYearArray = checkedMonthYear.split(SLASH)

        return when (monthYearArray.size) {
            1 -> { // Slash is not included
                when (checkedMonthYear.length) {
                    1 -> "$checkedMonthYear$SLASH"
                    2 -> {
                        return if (checkedMonthYear.toInt() <= 12) {
                            "$checkedMonthYear$SLASH"
                        } else {
                            StringBuilder(checkedMonthYear).insert(1, SLASH).toString()
                        }
                    }
                    else -> {
                        val first2Chars = checkedMonthYear.substring(0, 2)
                        return if (first2Chars.toInt() <= 12) {
                            val year = checkedMonthYear.substring(2, (checkedMonthYear.length).coerceAtMost(4))
                            "$first2Chars$SLASH$year"
                        } else {
                            val month = checkedMonthYear.substring(0, 1)
                            val year = checkedMonthYear.substring(1, (checkedMonthYear.length).coerceAtMost(3))
                            "$month$SLASH$year"
                        }
                    }
                }
            }
            else -> { // Slash is included
                val month = monthYearArray.first()
                val year = monthYearArray.last()

                when (month.length) {
                    0 -> {
                        return if (year.isEmpty()) { // "/"
                            ""
                        } else {
                            val yearEndIndex = year.length.coerceAtMost(2)
                            return "$SLASH${year.substring(0, yearEndIndex)}"
                        }
                    }
                    1 -> {
                        val yearEndIndex = year.length.coerceAtMost(2)
                        return "$month$SLASH${year.substring(0, yearEndIndex)}"
                    }
                    2 -> {
                        return if (month.toInt() <= 12) {
                            val yearEndIndex = year.length.coerceAtMost(2)
                            "$month$SLASH${year.substring(0, yearEndIndex)}"
                        } else {
                            val modifiedMonth = month.substring(0, 1)
                            val yearEndIndex = year.length.coerceAtMost(2)
                            "$modifiedMonth$SLASH${year.substring(0, yearEndIndex)}"
                        }
                    }
                    else -> {
                        val month2Chars = month.substring(0, 2)
                        return if (month2Chars.toInt() <= 12) {
                            val modifiedYear = month.substring(2, month.length) + year
                            val yearEndIndex = modifiedYear.length.coerceAtMost(2)
                            "$month2Chars$SLASH${modifiedYear.substring(0, yearEndIndex)}"
                        } else {
                            val modifiedMonth = month.substring(0, 1)
                            val yearEndIndex = year.length.coerceAtMost(2)
                            "$modifiedMonth$SLASH${year.substring(0, yearEndIndex)}"
                        }
                    }
                }
            }
        }
    }

    fun formatForDeletingChar(monthYear: CharSequence, beforeText: String): String {
        if (monthYear.isEmpty() || monthYear == SLASH) {
            return ""
        }

        val isSlashDeleted = beforeText.contains(SLASH) && !monthYear.contains(SLASH)
        if (isSlashDeleted) {
            val indexBeforeSlash = beforeText.indexOf(SLASH) - 1
            return monthYear.replaceRange(indexBeforeSlash, indexBeforeSlash + 1, SLASH).toString()
        }

        return monthYear.toString()
    }
}