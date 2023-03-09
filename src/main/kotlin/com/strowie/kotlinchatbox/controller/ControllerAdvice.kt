package com.strowie.kotlinchatbox.controller

import com.strowie.kotlinchatbox.model.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler
    fun handleResourceNotFound(e: ResourceNotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.getNotFoundMessage(), HttpStatus.NOT_FOUND)
    }
}