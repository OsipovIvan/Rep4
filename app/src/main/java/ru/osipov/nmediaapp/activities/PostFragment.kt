package ru.osipov.nmediaapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.osipov.nmediaapp.R
import ru.osipov.nmediaapp.activities.AppActivity.Companion.textArg
import ru.osipov.nmediaapp.adpter.PostClickListener
import ru.osipov.nmediaapp.databinding.FragmentPostBinding
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.model.PostViewModel
import ru.osipov.nmediaapp.utils.CONTENT_POST_KEY
import ru.osipov.nmediaapp.utils.LongArg

class PostFragment : Fragment() {

    companion object {
        var Bundle.longArg: Long? by LongArg
    }

    private val mViewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.longArg?.let {
            val id = it
            val listener = object : PostClickListener {
                override fun onLike(post: Post) {
                    mViewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    mViewModel.shareById(post.id)
                }

                override fun onRemove(post: Post) {
                    mViewModel.removeById(post.id)
                    findNavController().navigateUp()
                }

                override fun onEdit(post: Post) {
                    mViewModel.edit(post)
                    findNavController().navigate(
                        R.id.action_postFragment_to_newPostFragment3,
                        Bundle().apply {
                            putString(CONTENT_POST_KEY, post.content)
                        }
                    )
                }

                override fun navigate(post: Post) {

                }
            }

            mViewModel.data.observe(viewLifecycleOwner, {
                it.first {
                    it.id == id
                }.let { post ->
                    binding.apply {
                        titleTextView.text = post.author
                        publishTextView.text = post.published
                        descriptionTextView.text = post.content
                        likeImageButton.text = post.getLikes()
                        shareImageButton.text = post.getShare()
                        viewsImageButton.text = post.views.toString()
                        val video = post.video
                        if (video.isNotEmpty()) {
                            videoGroup.visibility = View.VISIBLE
                            videoImageView.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
                                val context = it.context
                                context.startActivity(intent)
                            }
                        }

                        likeImageButton.isChecked = post.likedByMe

                        likeImageButton.setOnClickListener {
                            listener.onLike(post)
                        }

                        shareImageButton.setOnClickListener {
                            listener.onShare(post)
                        }

                        menuImageButton.setOnClickListener {
                            PopupMenu(it.context, it).apply {
                                inflate(R.menu.options_post)
                                setOnMenuItemClickListener { menuItem ->
                                    when (menuItem.itemId) {
                                        R.id.menu_remove_post -> {
                                            listener.onRemove(post)
                                            true
                                        }
                                        R.id.menu_edit_post -> {
                                            listener.onEdit(post)
                                            true
                                        }
                                        else -> {
                                            false
                                        }
                                    }
                                }
                            }.show()
                        }
                    }

                }
            }
            )
        }

        return binding.root
    }
}
