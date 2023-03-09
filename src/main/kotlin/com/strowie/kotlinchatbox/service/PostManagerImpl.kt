package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.model.ResourceNotFoundException
import com.strowie.kotlinchatbox.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class PostManagerImpl(private val postRepository: PostRepository) : PostManager {

    override fun getPostFeed(): List<Post> {
        val pageRequest = PageRequest.of(0, 10, Post.sortByDefault)
        return postRepository.findAll(pageRequest).content
    }

    override fun createPost(author: String, content: String): Post {
        val post = Post(author, content, Instant.now())
        return postRepository.save(post)
    }

    override fun readPost(id: String): Post {
        return postRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Post with id [%s] could not be found".format(id)) }
    }

    override fun deletePost(id: String) {
        if(postRepository.existsById(id)) {
            //We could add some security
            postRepository.deleteById(id)
        } else {
            throw ResourceNotFoundException("Post with id [%s] could not be found".format(id))
        }
    }
}