package ru.nikita.mapmarks.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.nikita.mapmarks.db.AppDb
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.repository.Repository

class MarksViewModel(application: Application) : AndroidViewModel(application) {

    private val empty = Marks(
        id = 0,
        title = "",
        latitude = 0.0,
        longitude = 0.0,
    )

    private val repository: Repository = Repository(AppDb.getInstance(application).marksDao())
    val data = repository.allMarks()
    val edited = MutableLiveData(empty)

    fun save(marks: Marks) {
      repository.save(marks)
    }


    fun removeById(id: Int) = repository.removeMarksById(id)


    fun editById() {

    }

}