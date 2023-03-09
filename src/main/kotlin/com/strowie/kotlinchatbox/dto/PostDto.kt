package com.strowie.kotlinchatbox.dto

import com.strowie.kotlinchatbox.model.Post
import java.time.Instant

data class PostDto (
    private val id: String,
    private val source: String,
    private val content: String,
    private val emissionDate: Instant,
    private val nbLikes: Int,
){

    companion object {
        fun from(post: Post): PostDto {
            return PostDto(
                post.getId(),
                post.getSource(),
                post.getContent(),
                post.getEmissionDate(),
                post.getNbLikes()
            )
        }
    }

    private var responses: List<String> = listOf()

    fun getId(): String {
        return id;
    }

    fun getSource(): String {
        return source
    }

    fun getContent(): String {
        return content
    }

    fun getEmissionDate(): Instant {
        return emissionDate
    }

    fun getNbLikes(): Int {
        return nbLikes
    }

    fun getResponses(): List<String> {
        return responses
    }

    fun setResponses(responses: List<String>): PostDto {
        this.responses = responses
        return this
    }
}