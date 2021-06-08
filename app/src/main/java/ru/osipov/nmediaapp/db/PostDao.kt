package ru.osipov.nmediaapp.db

import ru.osipov.nmediaapp.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long) : Post
    fun removeById(id: Long) : List<Post>
    fun shareById(id: Long): Post
}