package ru.osipov.nmediaapp.model


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.osipov.nmediaapp.db.AppDb
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

class PostViewModel(application: Application): AndroidViewModel(application) {

    private var buffer: String? = null


    private val repository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )

    val data = repository.getAll()
    private val edited = MutableLiveData(empty)

    fun save(){
        edited.value?.let {
            repository.save(it)
        }

        edited.value  = empty
    }

    fun edit(post: Post){
        edited.value = post
    }

    fun changeContent(content: String){
        edited.value?.let {
            val text = content.trim()
            if (it.content == text){
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun getEdit(): Post?{
        return edited.value
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun getBuffer(): String?{
        return buffer
    }

    fun setBuffer(text: String?) {
        this.buffer = text
    }

}