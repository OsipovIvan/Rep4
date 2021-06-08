package ru.osipov.nmediaapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.repository.PostRepository

class PosRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll(): LiveData<List<Post>> =
        Transformations.map(dao.getAll()){ list ->
            list.map {
                Post(it.id, it.author, it.content, it.published, it.likes, it.likedByMe, it.share, it.views, it.video)
            }
        }

    override fun likeById(id: Long){
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }
}