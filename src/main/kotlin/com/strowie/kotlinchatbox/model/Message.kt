package com.strowie.kotlinchatbox.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
class Message(
    @Column private val source: String,
    @Column private val target: String,
    @Column(length = 4024) private val content: String) {

    @Id
    private val id: String = UUID.randomUUID().toString()

    fun getId(): String {
        return id
    }

    fun getSource(): String {
        return source
    }

    fun getTarget(): String {
        return target
    }

    fun getContent(): String {
        return content
    }
}