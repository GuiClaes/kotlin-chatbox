package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Post

interface PostManager {

    fun getPosts(): List<Post>

    fun getPostFeed(): List<Post>
}