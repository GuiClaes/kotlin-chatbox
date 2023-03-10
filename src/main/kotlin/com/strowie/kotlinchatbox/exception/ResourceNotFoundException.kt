package com.strowie.kotlinchatbox.exception

class ResourceNotFoundException(private val notFoundMessage: String?): RuntimeException() {

    fun getNotFoundMessage(): String {
        return notFoundMessage ?: "Resource not found"
    }
}