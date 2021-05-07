package ru.osipov.nmediaapp

import androidx.lifecycle.LiveData
import ru.osipov.nmediaapp.dto.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun likeById(id: Long)

    fun shareById(id: Long)

    fun removeById(id: Long)

    fun save(post: Post)
}