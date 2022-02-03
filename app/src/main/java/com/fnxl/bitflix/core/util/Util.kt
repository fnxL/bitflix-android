package com.fnxl.bitflix.core.util

fun <T> sendErrorSnackbar(result: Resource<T>): UiEvent {
    return UiEvent.SnackBar(
        result.exception?.localizedMessage ?: "Unknown Error Occurred"
    )
}

fun dateToYear(date: String): String {
    return date.substring(0, 4)
}

fun getRuntime(minutes: Int): String {
    if (minutes <= 60) return "$minutes" + "m"
    val hours = (minutes / 60 xor 0).toString().takeLast(2)

    val min = (minutes % 60).toString().takeLast(2)

    return hours + "h $min" + "m"
}
