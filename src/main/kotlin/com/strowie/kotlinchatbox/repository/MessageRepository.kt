package com.strowie.kotlinchatbox.repository

import com.strowie.kotlinchatbox.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, String> {

    //For example
    fun findByIdAndSource(id: String, source: String): Message?
}