package ru.nikita.mapmarks.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nikita.mapmarks.dto.Marks

interface Repo {
    fun allMarks(): LiveData<List<Marks>>
    fun removeMarks() {}
    fun editMarks() {}
}


class Repository : Repo {

    private var testMarks = listOf<Marks>(
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
    )

    private val data = MutableLiveData(testMarks)

    override fun allMarks(): LiveData<List<Marks>> = data
}