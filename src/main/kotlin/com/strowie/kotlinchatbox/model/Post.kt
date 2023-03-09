package com.strowie.kotlinchatbox.model

import jakarta.persistence.*
import org.springframework.data.domain.Sort
import java.time.Instant

@Entity
class Post(
    @Column
    private val source: String,

    @Column(length = 4024)
    private val content: String,

    @Column
    private val emissionDate: Instant,

    @Column
    private var nbLikes: Int = 0,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: String? = null,
) {

    companion object {
        val sortByDefault: Sort = Sort.by(Sort.Order.desc("emissionDate"))
    }

    fun getId(): String {
        return id ?: "undefined"
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

    fun getNbLikes(): Int {
        return nbLikes
    }

    fun addLike(): Post {
        nbLikes++
        return this
    }
}