package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Message

interface MessageManager {

    fun getMessages(): List<Message>
}