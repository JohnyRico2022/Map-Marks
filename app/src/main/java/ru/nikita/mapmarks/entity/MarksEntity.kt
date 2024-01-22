package ru.nikita.mapmarks.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nikita.mapmarks.dto.Marks

@Entity
data class MarksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val latitude: Double,
    val longitude: Double,
) {
    fun toDto() = Marks(id, title, latitude, longitude)

    companion object {
        fun fromDto(dto: Marks) =
            MarksEntity(
                dto.id,
                dto.title,
                dto.latitude,
                dto.longitude
            )
    }
}

fun List<MarksEntity>.toDto(): List<Marks> = map(MarksEntity::toDto)