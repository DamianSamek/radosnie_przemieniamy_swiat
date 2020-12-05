package com.wsiz.projekt_zespolowy.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.PaginationAdapter
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences

class PostsRecyclerViewAdapter(paginationContract: PaginationContract<UserPost>) :
    PaginationAdapter<PostsRecyclerViewAdapter.PostViewHolder, UserPost>(paginationContract) {

    private var currentUserId = -1

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = items[position]

        holder.apply {
            descriptionView.text = post.description
            Picasso.get().load(post.imageURL).into(imageView)

            itemView.setOnClickListener {
                if(post.userId == currentUserId) {
                    // Go to 4th tab
                } else {
                    // Go to user tab
                }
            }
        }
    }

    override fun paginationOnCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    inner class PostViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view) {
        val imageView: ImageView
        val descriptionView: TextView

        init {
            if (currentUserId == -1) currentUserId = SharedPreferences(view.context).getUserId()

            imageView = view.findViewById(R.id.imageView)
            descriptionView = view.findViewById(R.id.descriptionView)
        }
    }
}