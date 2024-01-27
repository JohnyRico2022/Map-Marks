package ru.nikita.mapmarks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nikita.mapmarks.entity.MarksEntity

@Dao
interface MarksDao {

    @Query("SELECT * FROM MarksEntity ORDER BY id DESC")
    fun getAll(): Flow<List<MarksEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marks: MarksEntity)

    @Query("UPDATE MarksEntity SET title = :title WHERE id = :id")
    suspend fun updateMarks(id: Long, title: String)


    @Query("DELETE FROM MarksEntity WHERE id = :id")
    suspend fun removeById(id: Long)

}
