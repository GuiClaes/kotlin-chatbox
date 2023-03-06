package com.strowie.kotlinchatbox.repository

import com.strowie.kotlinchatbox.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, String> {

    //For example
    fun findByIdAndSource(id: String, source: String): Post?
}