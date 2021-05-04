package ru.osipov.nmediaapp.model

import androidx.lifecycle.LiveData
import ru.osipov.nmediaapp.dto.Post

interface PostRepository {

    fun get(): LiveData<Post>

    fun like()

    fun share()
}