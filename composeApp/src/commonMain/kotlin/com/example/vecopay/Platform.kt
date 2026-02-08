package com.example.vecopay

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform