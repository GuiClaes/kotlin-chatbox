package com.strowie.kotlinchatbox.model

import jakarta.persistence.*
import org.springframework.data.domain.Sort
import java.time.Instant
import java.util.*

@Entity
class Post(
    @Column
    private val source: String,

    @Column(length = 4024)
    private val content: String,

    @Column
    private val emissionDate: Instant,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null,
) {

    companion object {
        val sortByDefault = Sort.by(Sort.Order.desc("emissionDate"))
    }

    fun getId(): Int? {
        return id
    }

    fun getSource(): String {
        return source
    }

    fun getContent(): String {
        return content
    }

    fun getEmissionDate(): Instant {
        return emissionDate
    }
}