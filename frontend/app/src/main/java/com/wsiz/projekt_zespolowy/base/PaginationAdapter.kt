package com.wsiz.projekt_zespolowy.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class PaginationAdapter<ItemsType>(
    private val paginationContract: PaginationContract<ItemsType>
) : RecyclerView.Adapter<PaginationAdapter.BasePaginationViewHolder>() {

    private enum class LoadingStatus {
        READY, LOADING, LAST_PAGE_LOADED
    }

    companion object {
        const val PAGE_NOT_SET = -1
        const val FIRST_PAGE = 0
        private const val LOADING_VIEW_TYPE = 1000
    }

    val isNoItems = MutableLiveData<Boolean>().apply { postValue(false) }

    private val compositeDisposable = CompositeDisposable()

    protected val items = mutableListOf<ItemsType>()

    private var pageNumber: Int = PAGE_NOT_SET

    private var loadingStatus: LoadingStatus = LoadingStatus.READY

    private fun loadMoreDataIfShould() {
        if (loadingStatus == LoadingStatus.READY) {
            loadMoreData()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.dispose()
    }

    private fun loadMoreData() {
        if (pageNumber == PAGE_NOT_SET) pageNumber = FIRST_PAGE else pageNumber++
        loadingStatus = LoadingStatus.LOADING
        compositeDisposable.add(
            paginationContract.loadMoreData(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ addItems(it) }, { paginationContract.onErrorLoading(it) })
        )
    }

    private fun addItems(newItems: List<ItemsType>) {
        if (newItems.isEmpty() && pageNumber == 0) onEmpty()

        loadingStatus = if (newItems.isEmpty()) {
            // last page was loaded

            notifyItemChanged(itemCount - 1)

            isNoItems.postValue(items.size == 0)
            LoadingStatus.LAST_PAGE_LOADED
        } else {
            items.addAll(newItems)
            notifyItemRangeInserted(itemCount - newItems.size, newItems.size)

            LoadingStatus.READY
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasePaginationViewHolder {
        return when (viewType) {
            LOADING_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.loading_view,
                    parent,
                    false
                )
                PaginationLoadingViewHolder(view)
            }
            else -> paginationOnCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> LOADING_VIEW_TYPE
            else -> paginationGetItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: BasePaginationViewHolder, position: Int) {
        if (getItemViewType(position) == LOADING_VIEW_TYPE) {
            holder as PaginationLoadingViewHolder

            if (loadingStatus != LoadingStatus.LAST_PAGE_LOADED) holder.progressBar.visibility =
                View.VISIBLE
            else holder.progressBar.visibility = View.GONE

            loadMoreDataIfShould()
        } else {
            paginationOnBindViewHolder(holder, position)
        }
    }

    protected open fun onEmpty() {}

    //+1 is loadingView
    override fun getItemCount() = paginationGetItemCount() + 1

    protected open fun paginationGetItemViewType(position: Int): Int {
        return -1
    }

    abstract fun paginationGetItemCount(): Int
    abstract fun paginationOnBindViewHolder(holder: BasePaginationViewHolder, position: Int)
    abstract fun paginationOnCreateViewHolder(parent: ViewGroup, viewType: Int): BasePaginationViewHolder


    open class BasePaginationViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class PaginationLoadingViewHolder(view: View) :
        BasePaginationViewHolder(view) {

        val progressBar: View = view.findViewById<ProgressBar>(R.id.loadingView)
    }

    interface PaginationContract<ItemsType> {
        fun loadMoreData(pageNumber: Int): Single<List<ItemsType>>
        fun onErrorLoading(error: Throwable)
    }
}