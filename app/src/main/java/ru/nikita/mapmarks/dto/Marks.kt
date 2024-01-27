package ru.nikita.mapmarks.dto

data class Marks(
    val id: Long = 0,
    val title: String,
    val latitude: Double,
    val longitude: Double,
)
