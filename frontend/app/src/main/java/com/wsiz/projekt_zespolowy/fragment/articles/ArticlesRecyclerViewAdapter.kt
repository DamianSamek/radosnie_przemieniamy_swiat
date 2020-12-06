package com.wsiz.projekt_zespolowy.fragment.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.PaginationAdapter
import com.wsiz.projekt_zespolowy.data.dto.Article

class ArticlesRecyclerViewAdapter(private val articleInteractionContract: ArticleInteractionContract) :
    PaginationAdapter<Article>(articleInteractionContract) {

    companion object {
        private const val HEADER_VIEW_TYPE = 1
        private const val ARTICLE_VIEW_TYPE = 2
    }

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: BasePaginationViewHolder, position: Int) {
        when (paginationGetItemViewType(position)) {
            ARTICLE_VIEW_TYPE -> {
                holder as ArticleViewHolder
                val article = items[position]

                holder.apply {
                    descriptionView.text = article.content
                    titleView.text = article.title
                    Picasso.get().load(article.imageURL).into(imageView)

                    itemView.setOnClickListener {
                        ViewCompat.setTransitionName(imageCardView, "articleTransition")
                        articleInteractionContract.onClick(imageCardView, article)
                    }
                }
            }
            HEADER_VIEW_TYPE -> {
                holder as HeaderViewHolder
            }
        }
    }

    override fun paginationOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasePaginationViewHolder {
        return when (viewType) {
            ARTICLE_VIEW_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.article_item, parent, false)
                ArticleViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
        }
    }

    override fun paginationGetItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE
        else ARTICLE_VIEW_TYPE
    }

    class ArticleViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val imageCardView: CardView = view.findViewById(R.id.imageCardView)
        val descriptionView: TextView = view.findViewById(R.id.descriptionView)
        val titleView: TextView = view.findViewById(R.id.titleView)
    }

    class HeaderViewHolder(view: View) : PaginationAdapter.BasePaginationViewHolder(view)

    interface ArticleInteractionContract : PaginationContract<Article> {
        fun onClick(cardView: CardView, article: Article)
    }
}