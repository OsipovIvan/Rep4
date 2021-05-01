package ru.osipov.nmediaapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import ru.osipov.nmediaapp.databinding.ActivityMainBinding
import ru.osipov.nmediaapp.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 999,
            likedByMe = false,
            share = 999_995
        )

        setupView(post, binding)
    }

    fun setupView(post: Post, binding: ActivityMainBinding) {

        with(binding) {
            titleTextView.text = post.author
            publishTextView.text = post.published
            descriptionTextView.text = post.content
            numberOfLikesTextView.text = post.getLikes()
            numberShare.text = post.getShare()
            numberViews.text = post.views.toString()

            likeImageButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    post.likes++
                    likeImageButton.setImageResource(R.drawable.ic_set_like_24_red)
                } else {
                    post.likes--
                    likeImageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                numberOfLikesTextView.text = post.getLikes()
            }
            shareImageButton.setOnClickListener {
                post.share++
                numberShare.text = post.getShare()
            }
        }
    }
}