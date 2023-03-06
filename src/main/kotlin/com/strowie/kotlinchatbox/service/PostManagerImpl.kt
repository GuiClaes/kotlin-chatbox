package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PostManagerImpl(private val postRepository: PostRepository) : PostManager {

    override fun getPosts(): List<Post> {
        return postRepository.findAll()
    }

    override fun getPostFeed(): List<Post> {
        val pageRequest = PageRequest.of(0, 10, Post.sortByDefault)
        return postRepository.findAll(pageRequest).content
    }
}