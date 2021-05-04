package ru.osipov.nmediaapp.adpter


import android.view.LayoutInflater
import android.view.ViewGroup
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
            numberOfLikesTextView.text = post.getLikes()
            numberShare.text = post.getShare()
            numberViews.text = post.views.toString()

            if (post.likedByMe) {
                likeImageButton.setImageResource(R.drawable.ic_set_like_24_red)
            } else {
                likeImageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            likeImageButton.setOnClickListener {
                listener.likeOnClickListener(post)
            }

            shareImageButton.setOnClickListener {
                listener.shareOnClickListener(post)
            }
        }
    }
}

interface PostClickListener{

    fun likeOnClickListener(post: Post)

    fun shareOnClickListener(post: Post)
}
