package ru.nikita.mapmarks.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nikita.mapmarks.dto.Marks

@Entity
data class MarksEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val latitude: Double,
    val longitude: Double,
) {
    fun toDto() = Marks(id = id, title = title, latitude = latitude, longitude = longitude)

    companion object {
        fun fromDto(dto: Marks): MarksEntity = with(dto) {
            MarksEntity(id = id, title = title, latitude = latitude, longitude = longitude)
        }
    }
}

//fun List<MarksEntity>.toDto(): List<Marks> = map(MarksEntity::toDto)