package ru.nikita.mapmarks.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nikita.mapmarks.entity.MarksEntity


@Dao
interface MarksDao {

    @Query("SELECT * FROM MarksEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<MarksEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marks: MarksEntity)

    @Query("DELETE FROM MarksEntity WHERE id = :id")
    fun removeById(id: Int)


 //   fun editById(id: Int)

}
