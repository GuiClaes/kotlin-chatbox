package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.repository.PostRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.Instant

@AutoConfigureMockMvc
@SpringBootTest
internal class PostControllerTest (@Autowired val mockMvc: MockMvc) {

    @Autowired
    lateinit var postRepository: PostRepository

    /*@Test
    fun getPosts() {
        every { postRepository.findAll() } returns emptyList()

        mockMvc.perform(MockMvcRequestBuilders
            .get("/messages")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

     */

    @Test
    fun getFeed() {
        val posts = listOf(
            Post("Me", "Hello world", Instant.now()),
            Post("Me", "Cool!", Instant.now()),
        )

        postRepository.saveAll(posts)

        mockMvc.perform(MockMvcRequestBuilders
            .get("/post/feed")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

}