package com.wsiz.projekt_zespolowy.base

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.data.dto.UserPost

open class BasePostsAdapter(
    private val postInteractionContract: PostInteractionContract
) : PaginationAdapter<BasePostsAdapter.PostViewHolder, UserPost>(postInteractionContract) {

    override fun paginationGetItemCount() = items.size


    override fun paginationOnBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = items[position]

        holder.apply {
            likeIconView.setImageResource(if (post.isLikedByMe) R.drawable.ic_like_filled else R.drawable.ic_like_empty)
            likeCountView.text = post.likesCount.toString()

            Picasso.get().load(post.imageURL).noFade().into(imageView)

            itemView.setOnClickListener {
                postInteractionContract.onPostClick(post)
            }

            likeIconView.setOnClickListener {
                postInteractionContract.onLikeClick(post)
                val likesCount = if (post.isLikedByMe) post.likesCount - 1 else post.likesCount + 1
                post.likesCount = likesCount
                post.isLikedByMe = !post.isLikedByMe
                notifyItemChanged(position, post)
            }

            likeCountView.setOnClickListener {
                likeIconView.callOnClick()
            }

            authorDescriptionView.text =
                getAuthorDescriptionSpannable(
                    itemView.context,
                    post.userName.toString(),
                    post.description
                )
        }
    }

    private fun getAuthorDescriptionSpannable(
        context: Context,
        author: String,
        description: String
    ): Spannable {
        val itemText = String.format(
            context.getString(R.string.post_author_description), author, description
        )
        val itemSpannable = SpannableString(itemText)

        val authorEnd = author.length + 1
        itemSpannable.setSpan(StyleSpan(Typeface.BOLD), 0, authorEnd, 0)

        return itemSpannable
    }

    override fun paginationOnCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    class PostViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val likeIconView: ImageView = view.findViewById(R.id.likeIconView)
        val likeCountView: TextView = view.findViewById(R.id.likeCountView)
        val authorDescriptionView: TextView = view.findViewById(R.id.authorDescriptionView)
    }

    interface PostInteractionContract : PaginationContract<UserPost> {
        fun onPostClick(userPost: UserPost)
        fun onLikeClick(userPost: UserPost)
    }
}