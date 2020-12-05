package com.wsiz.projekt_zespolowy.fragment.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.PaginationAdapter
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost

class PostsRecyclerViewAdapter(
    private val postAdapterContract: PostAdapterContract
) :
    PaginationAdapter<PostsRecyclerViewAdapter.PostViewHolder, UserPost>(postAdapterContract) {

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: PostViewHolder, position: Int) {
        val userPost = items[position]

        holder.apply {
            descriptionView.text = userPost.description
            Picasso.get().load(userPost.imageURL).into(imageView)

            itemView.setOnClickListener {
                postAdapterContract.onPostClick(userPost)
            }
        }
    }

    override fun paginationOnCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    class PostViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val descriptionView: TextView = view.findViewById(R.id.descriptionView)

    }

    interface PostAdapterContract : PaginationContract<UserPost> {
        fun onPostClick(post: UserPost)
    }
}