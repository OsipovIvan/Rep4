package ru.osipov.nmediaapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.osipov.nmediaapp.dto.Post

@Entity
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var share: Int = 0,
    var views: Int = 0,
    var video: String = ""
) {
    fun toDto() = Post(id, author, content, published, likes, likedByMe, share, views, video)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.likes,
                dto.likedByMe,
                dto.share,
                dto.views,
                dto.video
            )
    }
}