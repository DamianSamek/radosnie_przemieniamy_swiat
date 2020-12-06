package com.wsiz.projekt_zespolowy.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.utils.ImageUtils

open class BasePostsAdapter(
    private val postInteractionContract: PostInteractionContract,
    private val showHeader: Boolean = true
) : PaginationAdapter<UserPost>(postInteractionContract) {

    companion object {
        private const val HEADER_VIEW_TYPE = 1
        private const val POST_VIEW_TYPE = 2
    }

    private val loadedBitmaps = mutableMapOf<Int, Bitmap>()
    private val picassoTargets = mutableMapOf<Int, Target>()

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: BasePaginationViewHolder, position: Int) {
        when (paginationGetItemViewType(position)) {
            POST_VIEW_TYPE -> {
                holder as PostViewHolder

                val post = items[position]

                holder.apply {
                    likeIconView.setImageResource(if (post.isLikedByMe) R.drawable.ic_like_filled else R.drawable.ic_like_empty)
                    likeCountView.text = post.likesCount.toString()

                    val loadedBitmap = loadedBitmaps[position]
                    if (loadedBitmap == null) {
                        imageView.setImageResource(R.color.white)

                        val target = ImageUtils.generatePicassoTarget {
                            it?.let {
                                loadedBitmaps[position] = it
                                picassoTargets.remove(position)
                                imageView.setImageBitmap(it)
                            }
                        }
                        picassoTargets[position] = target
                        Picasso.get().load(post.imageURL).noFade().into(target)
                    } else {
                        imageView.setImageBitmap(loadedBitmap)
                    }

                    itemView.setOnClickListener {
                        ViewCompat.setTransitionName(imageCardView, "postTransition")
                        postInteractionContract.onPostClick(imageCardView, post)
                    }

                    likeIconView.setOnClickListener {
                        postInteractionContract.onLikeClick(post)
                        val likesCount =
                            if (post.isLikedByMe) post.likesCount - 1 else post.likesCount + 1
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
            HEADER_VIEW_TYPE -> {
                holder as HeaderViewHolder
            }
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

    override fun paginationGetItemViewType(position: Int): Int {
        return if (position == 0 && showHeader) HEADER_VIEW_TYPE
        else POST_VIEW_TYPE
    }

    override fun paginationOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasePaginationViewHolder {
        return when (viewType) {
            POST_VIEW_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
                PostViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
        }
    }

    class PostViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val imageCardView: CardView = view.findViewById(R.id.imageCardView)
        val likeIconView: ImageView = view.findViewById(R.id.likeIconView)
        val likeCountView: TextView = view.findViewById(R.id.likeCountView)
        val authorDescriptionView: TextView = view.findViewById(R.id.authorDescriptionView)
    }

    class HeaderViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view)

    interface PostInteractionContract : PaginationContract<UserPost> {
        fun onPostClick(cardView: CardView, userPost: UserPost)
        fun onLikeClick(userPost: UserPost)
    }
}