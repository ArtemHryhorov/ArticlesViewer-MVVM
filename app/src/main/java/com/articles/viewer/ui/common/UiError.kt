package com.articles.viewer.ui.common

import com.articles.viewer.core.exceptions.ServerUnavailableException
import java.net.UnknownHostException

data class UiError(
    val title: String,
    val description: String? = null,
) {

    // TODO("Add localisation by passing context")
    companion object {
        fun fromThrowable(error: Throwable) = when (error) {
            is ServerUnavailableException -> UiError(
                title = "Service is unavailable",
                description = "Try to connect with support team",
            )
            is UnknownHostException -> UiError(
                title = "Failed to load",
                description = "Check your Internet connection and try again later",
            )
            else -> UiError(title = "Something went wrong")
        }
    }
}