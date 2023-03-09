package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.dto.PostDto
import org.springframework.stereotype.Component

@Component
class PostEnricher {

    fun enrich(postDto: PostDto): PostDto {
        return postDto.setResponses(listOf("First answer ever", "Second one!!"))
    }
}