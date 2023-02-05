package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.dto.MessageDto
import com.strowie.kotlinchatbox.service.MessageManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val messageManager: MessageManager) {

    //FIXME Only for testing
    @GetMapping("/messages")
    fun getAllMessages(): List<MessageDto> {
        return messageManager.getMessages().stream()
            .map { MessageDto.from(it) }
            .toList()
    }
}