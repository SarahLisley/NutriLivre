package com.example.myapplication.domain.model

data class AppSettings(
    val darkModeEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val animationsEnabled: Boolean = true
)
