package ru.osipov.nmediaapp


import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {

    private val repository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()

    fun like(id: Long) = repository.like(id)

    fun share(id: Long) = repository.share(id)
}