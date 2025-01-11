package com.example.strathfinderapp.models

// MenuItem.kt
data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: Int,
    val activityClass: Class<*>
)
