package ru.osipov.nmediaapp.adpter


import android.view.LayoutInflater
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

            if (post.likedByMe) {
                likeImageButton.setIconResource(R.drawable.ic_set_like_24_red)
                likeImageButton.setIconTintResource(R.color.red)
            } else {
                likeImageButton.setIconResource(R.drawable.ic_baseline_favorite_border_24)
                likeImageButton.setIconTintResource(R.color.grey)
            }

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
