package ru.osipov.nmediaapp.adpter


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.osipov.nmediaapp.R
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.databinding.CardPostBinding

class PostAdapter(private val listener: PostClickListener) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.binding(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(post: Post) {
        binding.apply {
            titleTextView.text = post.author
            publishTextView.text = post.published
            descriptionTextView.text = post.content
            likeImageButton.text = post.getLikes()
            shareImageButton.text = post.getShare()
            viewsImageButton.text = post.views.toString()
            val video = post.video
            if (video.isNotEmpty()){
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

            menuImageButton.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId){
                            R.id.menu_remove_post -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.menu_edit_post ->{
                                listener.onEdit(post)
                                true
                            }
                            else ->{
                                false
                            }
                        }
                    }
                }.show()
            }
        }
    }
}

interface PostClickListener{

    fun onLike(post: Post)

    fun onShare(post: Post)

    fun onRemove(post: Post)

    fun onEdit(post: Post)
}
