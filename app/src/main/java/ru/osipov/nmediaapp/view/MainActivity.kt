package ru.osipov.nmediaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.NonNull
import ru.osipov.nmediaapp.R
import ru.osipov.nmediaapp.databinding.ActivityMainBinding
import ru.osipov.nmediaapp.model.dto.Post
import ru.osipov.nmediaapp.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mViewModel: PostViewModel by viewModels()
        mViewModel.data.observe(this, {
            setupView(it, binding)
        })

        setupClickListener(binding, mViewModel)
    }

    private fun setupClickListener(
        binding: ActivityMainBinding,
        mViewModel: PostViewModel
    ) {
        with(binding){
            likeImageButton.setOnClickListener {
                mViewModel.like()
             }

            shareImageButton.setOnClickListener {
                mViewModel.share()
            }
        }
    }

    fun setupView(post: Post, binding: ActivityMainBinding) {

        with(binding) {
            titleTextView.text = post.author
            publishTextView.text = post.published
            descriptionTextView.text = post.content
            numberOfLikesTextView.text = post.getLikes()
            numberShare.text = post.getShare()
            numberViews.text = post.views.toString()

            if (post.likedByMe) {
                likeImageButton.setImageResource(R.drawable.ic_set_like_24_red)
            } else {
                likeImageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }
}