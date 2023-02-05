package com.strowie.kotlinchatbox.service

import com.strowie.kotlinchatbox.model.Message
import com.strowie.kotlinchatbox.repository.MessageRepository
import org.springframework.stereotype.Component

@Component
class MessageManagerImpl(private val messageRepository: MessageRepository) : MessageManager {

    override fun getMessages(): List<Message> {
        return messageRepository.findAll()
    }
}