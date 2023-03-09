package com.strowie.kotlinchatbox.dto

class PostCreationDto {

    private lateinit var source: String
    private lateinit var content: String

    fun getSource(): String {
        return source
    }

    fun setSource(source: String): PostCreationDto {
        this.source = source
        return this
    }

    fun getContent(): String {
        return content
    }

    fun setContent(content: String): PostCreationDto {
        this.content = content
        return this
    }
}