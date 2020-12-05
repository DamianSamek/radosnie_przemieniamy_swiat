package com.wsiz.projekt_zespolowy.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.PaginationAdapter
import com.wsiz.projekt_zespolowy.data.dto.Post

class PostsRecyclerViewAdapter(paginationContract: PaginationContract<Post>) :
    PaginationAdapter<PostsRecyclerViewAdapter.PostViewHolder, Post>(paginationContract) {

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = items[position]

        holder.apply {
            descriptionView.text = post.description
            Picasso.get().load(post.imageURL).into(imageView)
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
}