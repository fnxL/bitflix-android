package com.fnxl.bitflix.core.util

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NetworkError {
    companion object {
        fun <T> resolveError(e: Exception): Resource<T> {

            val error = when (e) {
                is SocketTimeoutException -> {
                    NetworkErrorException(errorMessage = "Oops! Couldn't reach server. Check your internet connection")
                }
                is ConnectException -> {

                    NetworkErrorException(errorMessage = "Oops! Couldn't reach server. Check your internet connection")
                }
                is UnknownHostException -> {
                    NetworkErrorException(errorMessage = "Oops! Couldn't reach server. Check your internet connection")
                }
                is HttpException -> {
                    when (e.code()) {
                        502 -> {
                            NetworkErrorException(e.code(), "Internal Server Error")
                        }
                        401 -> {
                            NetworkErrorException.parseException(e)
                        }
                        400 -> {
                            NetworkErrorException.parseException(e)
                        }
                        else -> NetworkErrorException(e.code(), "Unknown Error")
                    }
                }
                else -> NetworkErrorException(errorMessage = "Unknown Error")
            }
            return Resource.Error(error)
        }
    }
}
