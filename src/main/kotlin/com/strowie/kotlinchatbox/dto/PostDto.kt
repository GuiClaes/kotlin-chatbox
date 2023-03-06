package com.strowie.kotlinchatbox.dto

import com.strowie.kotlinchatbox.model.Post
import java.time.Instant

class PostDto {

    companion object {
        fun from(post: Post): PostDto {
            return PostDto()
                .setSource(post.getSource())
                .setContent(post.getContent())
                .setEmissionDate(post.getEmissionDate())
        }
    }

    private var source: String? = null
    private var content: String? = null
    private var emissionDate: Instant? = null

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String): PostDto {
        this.source = source
        return this
    }

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String): PostDto {
        this.content = content
        return this
    }

    fun getEmissionDate(): Instant? {
        return emissionDate
    }

    fun setEmissionDate(emissionDate: Instant): PostDto {
        this.emissionDate = emissionDate
        return this
    }
}