package ru.osipov.nmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.osipov.nmediaapp.adpter.PostAdapter
import ru.osipov.nmediaapp.adpter.PostClickListener
import ru.osipov.nmediaapp.databinding.ActivityMainBinding
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.utils.AndroidUtils

class MainActivity : AppCompatActivity(), PostClickListener {

    private val mViewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter(this)

        mViewModel.data.observe(this, {
            adapter.submitList(it)
        })

        binding.list.adapter = adapter

        binding.imageButtonPostList.setOnClickListener{
            with(binding.editTextPostList){
                if (text.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, context.getString(R.string.toast_not_content), Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                mViewModel.changeContent(text.toString())
                mViewModel.save()

                clearFocus()
                setText("")
                AndroidUtils.hideKeyboard(this)
            }
        }
    }

    override fun likeOnClickListener(post: Post) {
        mViewModel.likeById(post.id)
    }

    override fun shareOnClickListener(post: Post) {
        mViewModel.shareById(post.id)
    }

    override fun removeOnClickListener(post: Post) {
        mViewModel.removeById(post.id)
    }
}