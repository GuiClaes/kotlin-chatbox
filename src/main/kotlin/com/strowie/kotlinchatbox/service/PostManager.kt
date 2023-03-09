package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Post

interface PostManager {

    fun getPostFeed(): List<Post>

    fun createPost(author: String, content: String): Post

    fun readPost(id: String): Post

    fun deletePost(id: String)
}