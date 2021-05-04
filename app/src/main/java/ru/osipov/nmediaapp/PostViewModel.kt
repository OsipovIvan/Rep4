package ru.osipov.nmediaapp


import androidx.lifecycle.ViewModel
import ru.osipov.nmediaapp.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository = PostRepositoryInMemoryImpl()

    val data = repository.get()

    fun like() = repository.like()

    fun share() = repository.share()
}