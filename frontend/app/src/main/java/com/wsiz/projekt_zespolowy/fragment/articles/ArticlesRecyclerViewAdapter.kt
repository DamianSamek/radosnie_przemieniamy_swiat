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
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.PaginationAdapter
import com.wsiz.projekt_zespolowy.data.dto.Article

class ArticlesRecyclerViewAdapter(private val articleInteractionContract: ArticleInteractionContract) :
    PaginationAdapter<ArticlesRecyclerViewAdapter.ArticleViewHolder, Article>(
        articleInteractionContract
    ) {

    companion object {
        private const val HEADER_VIEW_TYPE = 1
        private const val ARTICLE_VIEW_TYPE = 2
    }

    override fun paginationGetItemCount() = items.size

    override fun paginationOnBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = items[position]

        holder.apply {
            descriptionView.text = article.content
            titleView.text = article.title
            Picasso.get().load(article.imageURL).into(imageView)

            itemView.setOnClickListener {
                ViewCompat.setTransitionName(imageCardView, "articleTransition")
                ViewCompat.setTransitionName(titleView, "articleTransitionTitle")
                ViewCompat.setTransitionName(descriptionView, "articleTransitionContent")
                ViewCompat.setTransitionName(separatorView, "articleTransitionSeparator")
                articleInteractionContract.onClick(imageCardView, titleView, descriptionView, separatorView, article)
            }
        }
    }

    override fun paginationOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
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
        val separatorView: View = view.findViewById(R.id.separatorView)
    }

    interface ArticleInteractionContract : PaginationContract<Article> {
        fun onClick(cardView: CardView, titleView: TextView, contentView: TextView, separatorView: View, article: Article)
    }
}