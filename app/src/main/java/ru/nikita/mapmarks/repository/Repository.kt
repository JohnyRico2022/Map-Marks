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
    fun editMarks()
    fun save(marks: Marks)
}


class Repository(
    private val dao : MarksDao
) : Repo {

 /*   private var testMarks = listOf<Marks>(
        Marks(1, "Метка 1", 66.545, 33.555),
        Marks(2, "Метка 2", 77.545, 33.555),
        Marks(3, "Метка 3", 69.545, 33.555),
        Marks(4, "Метка 4", 66.545, 33.555),
        Marks(5, "Метка 5", 12.545, 33.555),
        Marks(6, "Метка 6", 63.545, 33.555),
        Marks(7, "Метка 7", 66.545, 33.555),
        Marks(8, "Метка 8", 66.545, 33.555),
        Marks(9, "Метка 9", 66.545, 33.555),
        Marks(10, "Метка 10", 66.545, 33.555),
    )*/

  //  private val data = MutableLiveData()

    override fun allMarks(): LiveData<List<Marks>> = dao.getAll().map{list ->
        list.map { Marks(it.id, it.title, it.latitude, it.longitude) }

    }
    override fun removeMarksById(id: Int) {
        dao.removeById(id)
    }

    override fun editMarks() {
        TODO("Not yet implemented")
    }

    override fun save(marks: Marks) {
        dao.insert(MarksEntity.fromDto(marks))
    }
}