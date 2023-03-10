package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.exception.ResourceNotFoundException
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

    override fun createPost(author: String, content: String, parent: String?): Post {
        if(parent != null) {
            val parentPost = postRepository.findById(parent)
                .orElseThrow { ResourceNotFoundException("Post with id [%s] could not be found".format(parent)) }

            val post = Post(author, content, Instant.now(), parent = parentPost)
            parentPost.addChild(post)

            return postRepository.save(post)
        } else {
            val post = Post(author, content, Instant.now())
            return postRepository.save(post)
        }
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