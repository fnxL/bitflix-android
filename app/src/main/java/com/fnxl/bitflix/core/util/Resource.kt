package com.fnxl.bitflix.core.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
    val data: T? = null,
    val exception: NetworkErrorException? = null,
    val title: String? = null,
) {
    class Success<T>(data: T?, title: String? = null) : Resource<T>(data, title = title)
    class Error<T>(exception: NetworkErrorException, data: T? = null) : Resource<T>(data, exception)
}

