package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.dto.PostCreationDto
import com.strowie.kotlinchatbox.dto.PostDto
import com.strowie.kotlinchatbox.service.PostManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postManager: PostManager,
    private val postEnricher: PostEnricher
) {

    @GetMapping("/post/feed")
    fun getPostFeed(): List<PostDto> {
        return postManager.getPostFeed()
            .map { PostDto.from(it) }
    }

    @PostMapping("/post")
    fun createPost(@RequestBody creationDto: PostCreationDto): PostDto {
        val createdPost = postManager.createPost(creationDto.getSource(), creationDto.getContent(), creationDto.getParent())
        return postEnricher.enrich(PostDto.from(createdPost))
    }

    @GetMapping("/post/{id}")
    fun readPost(@PathVariable id: String): PostDto {
        val post = postManager.readPost(id)
        return postEnricher.enrich(PostDto.from(post))
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(@PathVariable id: String) {
        postManager.deletePost(id)
    }
}