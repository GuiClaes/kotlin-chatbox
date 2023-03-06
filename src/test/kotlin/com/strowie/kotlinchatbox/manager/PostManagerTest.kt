package com.strowie.kotlinchatbox.manager

import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.repository.PostRepository
import com.strowie.kotlinchatbox.service.PostManager
import com.strowie.kotlinchatbox.service.PostManagerImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

class PostManagerTest {

    private val postRepository: PostRepository = mockk()
    private val postManager: PostManager = PostManagerImpl(postRepository)

    @Test
    fun findAll() {
        val post = Post("I", "Hello world!", Instant.now())
        every { postRepository.findAll() } returns listOf(post)

        val messages = postManager.getPosts()

        verify(exactly = 1) { postRepository.findAll() }
        Assertions.assertThat(messages).contains(post)
    }
}