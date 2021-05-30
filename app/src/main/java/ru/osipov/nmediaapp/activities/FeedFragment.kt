package ru.osipov.nmediaapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.osipov.nmediaapp.R
import ru.osipov.nmediaapp.adpter.PostAdapter
import ru.osipov.nmediaapp.adpter.PostClickListener
import ru.osipov.nmediaapp.databinding.FragmentFeedBinding
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.model.PostViewModel
import ru.osipov.nmediaapp.utils.CONTENT_POST_KEY
import ru.osipov.nmediaapp.utils.LongArg

class FeedFragment : Fragment() {

    private val mViewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    companion object {
        var Bundle.longArg: Long? by LongArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

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
                findNavController().navigate(R.id.action_feedFragment2_to_newPostFragment3, Bundle().apply {
                    putString(CONTENT_POST_KEY, post.content)
                })
            }

            override fun navigate(post: Post) {
                findNavController().navigate(R.id.action_feedFragment2_to_postFragment, Bundle().apply {
                    longArg = post.id
                })
            }
        })

        mViewModel.data.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.list.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment2_to_newPostFragment3)
        }

        return binding.root
    }

    fun launchNewPostActivity(mViewModel: PostViewModel, newPostLauncher: ActivityResultLauncher<String?>){
        val post = mViewModel.getEdit()
        newPostLauncher.launch(post?.content)
    }
}
