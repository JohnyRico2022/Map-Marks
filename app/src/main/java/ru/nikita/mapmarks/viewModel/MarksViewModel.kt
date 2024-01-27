package ru.nikita.mapmarks.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.nikita.mapmarks.db.AppDb
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.entity.MarksEntity

class MarksViewModel(context: Application) : AndroidViewModel(context) {

    private val dao = AppDb.getInstance(context).marksDao()

    val places = dao.getAll().map {
        it.map(MarksEntity::toDto)
    }

    fun save(marks: Marks) {
        viewModelScope.launch {
            dao.insert(MarksEntity.fromDto(marks))
        }
    }

    fun removeById(id: Long) {
        viewModelScope.launch {
            dao.removeById(id)
        }
    }

    fun editById(id: Long, title: String) {

        viewModelScope.launch {
            dao.changeTitleById(id, title)
        }
    }

}