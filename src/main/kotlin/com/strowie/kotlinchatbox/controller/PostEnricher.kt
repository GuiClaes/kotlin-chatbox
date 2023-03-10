package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.dto.PostDto
import com.strowie.kotlinchatbox.exception.ResourceNotFoundException
import com.strowie.kotlinchatbox.repository.PostRepository
import org.springframework.stereotype.Component

@Component
class PostEnricher (private val postRepository: PostRepository){

    fun enrich(postDto: PostDto): PostDto {
        val post = postRepository.findById(postDto.getId())
            .orElseThrow { ResourceNotFoundException("Post with id [%s] could not be found".format(postDto.getId())) }

        val responses = post.getChildren()
            .sortedBy { it.getEmissionDate() }
            .map { it.getContent() }

        return postDto.setResponses(responses)
    }
}