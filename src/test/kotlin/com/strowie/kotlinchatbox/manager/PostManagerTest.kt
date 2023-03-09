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

}