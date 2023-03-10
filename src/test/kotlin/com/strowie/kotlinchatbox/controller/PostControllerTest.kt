package com.strowie.kotlinchatbox.controller

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.strowie.kotlinchatbox.dto.PostCreationDto
import com.strowie.kotlinchatbox.model.Post
import com.strowie.kotlinchatbox.repository.PostRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.Instant
import kotlin.test.assertFailsWith

@AutoConfigureMockMvc
@SpringBootTest
internal class PostControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @Transactional
    fun getPostFeed_ordered() {
        val firstPost = Post("Me", "Hello world, this is the first message of the site.", Instant.now())
        val secondPost = Post("Me", "Cool app!", Instant.now())

        postRepository.saveAll(listOf(firstPost, secondPost))

        mockMvc.perform(MockMvcRequestBuilders
            .get("/post/feed")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(secondPost.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].source").value(secondPost.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value(secondPost.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].emissionDate").value(secondPost.getEmissionDate().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbLikes").value("0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].responses").isEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(firstPost.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].source").value(firstPost.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value(firstPost.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].emissionDate").value(firstPost.getEmissionDate().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nbLikes").value("0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].responses").isEmpty)
    }

    @Test
    @Transactional
    fun getPostFeed_noPost() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/post/feed")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty)
    }

    @Test
    @Transactional
    fun createPost() {
        val creationDto = PostCreationDto()
            .setSource("Me")
            .setContent("I created my first post")

        mockMvc.perform(MockMvcRequestBuilders
            .post("/post")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(creationDto)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(creationDto.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(creationDto.getContent()))
    }

    @Test
    @Transactional
    fun createResponsePost() {
        val post = Post("Me", "I created my first post")
        postRepository.save(post)

        val responseDto = PostCreationDto()
            .setSource("Me")
            .setContent("This is the first response")
            .setParent(post.getId())

        mockMvc.perform(MockMvcRequestBuilders
            .post("/post")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(responseDto)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(responseDto.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(responseDto.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.responses").isEmpty)


        mockMvc.perform(MockMvcRequestBuilders
            .get("/post/" + post.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(post.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(post.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emissionDate").value(post.getEmissionDate().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nbLikes").value(post.getNbLikes()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.responses[0]").value("This is the first response"))
    }

    @Test
    @Transactional
    fun create_contentMissing() {
        val creationDto = PostCreationDto()
            .setSource("Me")

        assertFailsWith<JsonMappingException> {
            mockMvc.perform(MockMvcRequestBuilders
                .post("/post")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
        }
    }

    @Test
    @Transactional
    fun create_sourceMissing() {
        val creationDto = PostCreationDto()
            .setContent("We are anonymous")

        assertFailsWith<JsonMappingException> {
            mockMvc.perform(MockMvcRequestBuilders
                .post("/post")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
        }
    }

    @Test
    @Transactional
    fun readPost() {

        val post = Post("Me", "Hello world!", Instant.now())
        postRepository.save(post)

        mockMvc.perform(MockMvcRequestBuilders
            .get("/post/" + post.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(post.getSource()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(post.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emissionDate").value(post.getEmissionDate().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nbLikes").value(post.getNbLikes()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.responses").isEmpty)
    }

    @Test
    @Transactional
    fun deletePost() {
        val post = Post("Me", "Mhhhh might delete later!", Instant.now())
        postRepository.save(post)

        mockMvc.perform(MockMvcRequestBuilders
            .delete("/post/" + post.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)


        Assertions.assertThat(postRepository.findById(post.getId())).isEmpty
    }

    @Test
    @Transactional
    fun deletePost_notFound() {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/post/" + "RandomId")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

}