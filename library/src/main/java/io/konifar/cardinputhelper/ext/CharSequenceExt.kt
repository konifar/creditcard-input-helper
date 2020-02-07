package io.konifar.cardinputhelper.ext

fun CharSequence.digits(): String {
    val builder = StringBuilder()
    for (c in this) {
        if (c.isDigit()) {
            builder.append(c)
        }
    }
    return builder.toString()
}

fun CharSequence.digitsAndSlash(): String {
    val builder = StringBuilder()
    for (c in this) {
        if (c.isDigit() || c == "/".toCharArray().first()) {
            builder.append(c)
        }
    }
    return builder.toString()
}