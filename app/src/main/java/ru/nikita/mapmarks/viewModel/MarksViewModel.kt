package ru.nikita.mapmarks.viewModel

import androidx.lifecycle.ViewModel
import ru.nikita.mapmarks.repository.Repository

class MarksViewModel : ViewModel() {

    private val repository: Repository = Repository()
    val data = repository.allMarks()

    fun removeById() {

    }

    fun editById() {

    }

}