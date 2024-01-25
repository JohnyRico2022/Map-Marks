package ru.nikita.mapmarks.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.nikita.mapmarks.dao.MarksDao
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.entity.MarksEntity

interface Repo {
    fun allMarks(): LiveData<List<Marks>>
    fun removeMarksById(id: Int)
    fun editMarksById()
    fun save(marks: Marks)
}


class Repository(
    private val dao: MarksDao
) : Repo {

    //  private val data = MutableLiveData()

    override fun allMarks(): LiveData<List<Marks>> = dao.getAll().map { list ->
        list.map { it.toDto() }


        /*list.map {
            Marks(it.id, it.title, it.latitude, it.longitude)
        }*/

    }

    override fun removeMarksById(id: Int) = dao.removeById(id)


    override fun editMarksById() {
        //      dao.updateTitleById(id,title)
    }

    override fun save(marks: Marks) = dao.insert(MarksEntity.fromDto(marks))

}