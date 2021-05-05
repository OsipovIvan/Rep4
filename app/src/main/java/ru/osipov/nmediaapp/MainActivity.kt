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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mViewModel: PostViewModel by viewModels()

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
            }
        })

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

        mViewModel.edited.observe(this, {
            with(binding.editTextPostList){
                requestFocus()
                setText(it.content)
            }
        })
    }
}