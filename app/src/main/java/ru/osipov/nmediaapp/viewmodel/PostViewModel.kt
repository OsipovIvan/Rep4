package ru.osipov.nmediaapp.viewmodel


import androidx.lifecycle.ViewModel
import ru.osipov.nmediaapp.model.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository = PostRepositoryInMemoryImpl()

    val data = repository.get()

    fun like() = repository.like()

    fun share() = repository.share()
}