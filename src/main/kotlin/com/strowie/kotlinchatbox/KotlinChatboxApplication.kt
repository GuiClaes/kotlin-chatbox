package com.strowie.kotlinchatbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [KotlinChatboxApplication::class])
@EntityScan("com.strowie.kotlinchatbox")
class KotlinChatboxApplication {
}

fun main(args: Array<String>) {
    runApplication<KotlinChatboxApplication>(*args)
}