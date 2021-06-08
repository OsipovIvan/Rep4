package ru.osipov.nmediaapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.osipov.nmediaapp.db.PostDao
import ru.osipov.nmediaapp.dto.Post

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        val saved = dao.likeById(id)
        posts = mapPosts(id, saved)
        data.value = posts
    }

    override fun shareById(id: Long) {
        val saved = dao.shareById(id)
        posts = mapPosts(id, saved)
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = dao.removeById(id)
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L){
            listOf(saved) + posts
        } else{
            mapPosts(id, saved)
        }
        data.value = posts
    }

    private fun mapPosts(
        id: Long,
        saved: Post
    ) = posts.map {
        if (it.id != id) it else saved
    }
}