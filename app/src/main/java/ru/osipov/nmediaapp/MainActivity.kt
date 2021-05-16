package ru.osipov.nmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import ru.osipov.nmediaapp.activities.NewPostResultContract
import ru.osipov.nmediaapp.adpter.PostAdapter
import ru.osipov.nmediaapp.adpter.PostClickListener
import ru.osipov.nmediaapp.databinding.ActivityMainBinding
import ru.osipov.nmediaapp.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mViewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult

            mViewModel.changeContent(result)
            mViewModel.save()
        }

        val adapter = PostAdapter(object : PostClickListener{
            override fun onLike(post: Post) {
                mViewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                mViewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                mViewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                mViewModel.edit(post)
                launchNewPostActivity(mViewModel, newPostLauncher)
            }
        })

        mViewModel.data.observe(this, {
            adapter.submitList(it)
        })

        binding.list.adapter = adapter

        binding.fab.setOnClickListener {
            launchNewPostActivity(mViewModel, newPostLauncher)
        }
    }

    fun launchNewPostActivity(mViewModel: PostViewModel, newPostLauncher: ActivityResultLauncher<String?>){
        val post = mViewModel.getEdit()
        newPostLauncher.launch(post?.content)
    }
}