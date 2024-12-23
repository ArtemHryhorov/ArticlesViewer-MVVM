package com.articles.viewer.ui.common

import com.articles.viewer.core.exceptions.ServerUnavailableException

data class UiError(
    val title: String,
    val description: String? = null,
) {

    // TODO("Localisation")
    companion object {
        fun fromThrowable(error: Throwable) = when (error) {
            is ServerUnavailableException -> UiError(
                title = "Service is unavailable",
                description = "Try to connect with support team",
            )
            else -> UiError(title = "Something went wrong")
        }
    }
}