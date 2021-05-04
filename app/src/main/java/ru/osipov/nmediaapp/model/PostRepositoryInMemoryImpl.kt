package ru.osipov.nmediaapp.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.osipov.nmediaapp.model.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {

    private val post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likes = 999,
        likedByMe = false,
        share = 999_995
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val like = !post.likedByMe
        post.likedByMe = like
        if (like) post.likes++ else post.likes--
        data.value = post
    }

    override fun share() {
        post.share++
        data.value = post
    }
}