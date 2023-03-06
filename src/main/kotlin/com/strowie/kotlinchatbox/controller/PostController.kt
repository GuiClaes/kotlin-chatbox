package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.dto.PostDto
import com.strowie.kotlinchatbox.service.PostManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(private val postManager: PostManager) {

    //FIXME: Only for testing
    @GetMapping("/messages")
    fun getAllMessages(): List<PostDto> {
        return postManager.getPosts()
            .map { PostDto.from(it) }
    }

    @GetMapping("/post/feed")
    fun getPostFeed(): List<PostDto> {
        return postManager.getPostFeed()
            .map { PostDto.from(it) }
    }
}