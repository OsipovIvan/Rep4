package ru.osipov.nmediaapp.model

import androidx.lifecycle.LiveData
import ru.osipov.nmediaapp.dto.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun like(id: Long)

    fun share(id: Long)
}