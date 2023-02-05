package com.strowie.kotlinchatbox.dto

import com.strowie.kotlinchatbox.model.Message

class MessageDto {

    companion object {
        fun from(message: Message): MessageDto {
            return MessageDto()
                .setSource(message.getSource())
                .setTarget(message.getTarget())
                .setContent(message.getContent())
        }
    }

    private var source: String? = null
    private var target: String? = null
    private var content: String? = null

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String): MessageDto {
        this.source = source
        return this
    }

    fun getTarget(): String? {
        return target
    }

    fun setTarget(target: String): MessageDto {
        this.target = target
        return this
    }

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String): MessageDto {
        this.content = content
        return this
    }
}