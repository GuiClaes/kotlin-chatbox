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
    private val emissionDate: Instant = Instant.now(),

    @Column
    private var nbLikes: Int = 0,

    @ManyToOne
    private var parent: Post? = null,

    @OneToMany
    private val children: MutableList<Post> = mutableListOf(),

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
        this.nbLikes++
        return this
    }

    fun getParent(): Post? {
        return parent
    }

    fun getChildren(): List<Post> {
        return children
    }

    fun addChild(post: Post): Post {
        children.add(post)
        return this
    }
}