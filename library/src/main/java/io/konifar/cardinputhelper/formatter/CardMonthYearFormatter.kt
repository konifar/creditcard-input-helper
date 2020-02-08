package io.konifar.cardinputhelper.formatter

import io.konifar.cardinputhelper.ext.digitsAndSlash

object CardMonthYearFormatter {
    const val SLASH = "/"

    fun formatForInserting(monthYear: CharSequence): String {
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
                            val extraMonth = month.substring(1, 2)
                            val modifiedYear = if (year.isEmpty()) extraMonth else year
                            val yearEndIndex = modifiedYear.length.coerceAtMost(2)
                            "$modifiedMonth$SLASH${modifiedYear.substring(0, yearEndIndex)}"
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

    fun formatForDeleting(monthYear: CharSequence, beforeText: String): String {
        if (monthYear.isEmpty() || monthYear == SLASH) {
            return ""
        }

        val isSlashDeleted = beforeText.contains(SLASH) && !monthYear.contains(SLASH)
        if (isSlashDeleted) {
            val indexBeforeSlash = (beforeText.indexOf(SLASH) - 1)
            if (indexBeforeSlash < 0) {
                return "$SLASH$monthYear"
            } else {
                val modified = monthYear.replaceRange(indexBeforeSlash, indexBeforeSlash + 1, SLASH).toString()
                return if (modified == SLASH) {
                    ""
                } else {
                    modified
                }
            }
        }

        return monthYear.toString()
    }

    fun calculateCursorPos(formatted: CharSequence, originalAfter: CharSequence, originalBefore: CharSequence): Int {
        if (formatted.isEmpty()) {
            return 0
        }

        val isPasted = originalAfter.length - originalBefore.length > 1
        if (isPasted) {
            return formatted.length - 1
        }

        val originalCursorPos = getCurrentCursorPos(originalAfter, originalBefore)

        val isInserted = originalBefore.length < originalAfter.length
        if (isInserted) {
            val isSlashInserted = !originalAfter.contains(SLASH) && formatted.contains(SLASH)
            if (isSlashInserted) {
                val month = formatted.split(SLASH).first()
                return if (month == "0" || month == "1") {
                    formatted.indexOf(SLASH)
                } else {
                    formatted.length
                }
            }

            if (originalAfter[originalCursorPos].toString() == SLASH) {
                if (originalCursorPos == 0) {
                    return 0
                }
                return originalCursorPos
            }

            val originalSlashPos = originalAfter.indexOf(SLASH)
            val isMonthInserted = originalCursorPos < originalSlashPos
            val month = originalAfter.split(SLASH).first()
            if (isMonthInserted) {
                val year = originalAfter.split(SLASH).last()
                if (year.isEmpty()) {
                    if (month.toInt() <= 12) {
                        if (month == "0" || month == "1") {
                            return formatted.indexOf(SLASH)
                        }
                        return formatted.indexOf(SLASH) + 1
                    } else {
                        return formatted.indexOf(SLASH) + 2
                    }
                } else {
                    return originalCursorPos + 1
                }
            }

            return (originalCursorPos + 1).coerceAtMost(formatted.length)
        }

        val isDeleted = originalBefore.length > formatted.length
        if (isDeleted) {
            val isSlashModified = !originalAfter.contains(SLASH) && formatted.contains(SLASH)
            if (isSlashModified) {
                return formatted.indexOf(SLASH)
            }
            return originalCursorPos
        }

        return formatted.length
    }

    private fun getCurrentCursorPos(originalAfter: CharSequence, originalBefore: CharSequence): Int {
        if (originalAfter.length <= originalBefore.length) {
            for ((idx, c) in originalAfter.withIndex()) {
                if (c != originalBefore[idx]) {
                    return idx
                }
            }
            return originalAfter.length
        } else {
            for ((idx, c) in originalBefore.withIndex()) {
                if (c != originalAfter[idx]) {
                    return idx
                }
            }
            return originalAfter.length - 1
        }
    }

    private fun hasMultipleSlash(text: String): Boolean {
        val firstSlashIndex = text.indexOf(SLASH)
        val lastSlashIndex = text.lastIndexOf(SLASH)
        return firstSlashIndex >= 0 && lastSlashIndex >= 0 && firstSlashIndex != lastSlashIndex
    }

    private fun removeUnnecessarySlash(text: String): String {
        if (hasMultipleSlash(text)) {
            val firstSlashIndex = text.indexOf(SLASH)
            val modified = text.replace(SLASH, "")
            return StringBuilder(modified).insert(firstSlashIndex, SLASH).toString()
        }
        return text
    }
}