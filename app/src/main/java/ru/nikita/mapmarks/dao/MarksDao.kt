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

   /* @Query("UPDATE MarksEntity SET title = :title WHERE id = :id")
    fun changeTitleById(id: Long, title: String)*/

    /*fun save(marks: MarksEntity) =
        if (marks.id == 0) insert(marks) else changeTitleById(marks.id, marks.title)*/

    @Query("DELETE FROM MarksEntity WHERE id = :id")
    fun removeById(id: Long)

}
