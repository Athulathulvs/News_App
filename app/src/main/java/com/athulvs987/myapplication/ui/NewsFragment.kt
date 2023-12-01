package com.athulvs987.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.athulvs987.myapplication.api.RetrofitInstance
import com.athulvs987.myapplication.databinding.FragmentNewsBinding
import com.athulvs987.myapplication.databinding.LayoutErrorBinding
import com.athulvs987.myapplication.model.newsModel.NewsResponce
import com.athulvs987.myapplication.presenter.NewsPresenter
import com.athulvs987.myapplication.presenter.NewsView
import com.athulvs987.myapplication.ui.adapter.NewsAdapter
import com.athulvs987.myapplication.utils.Constance
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment(),NewsView {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var errorBinding: LayoutErrorBinding


    private var isLoading =false
    private var isFullyLoaded=false

    lateinit var adapter: NewsAdapter
    lateinit var presenter: NewsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
      binding= FragmentNewsBinding.inflate(inflater,container,false)
        errorBinding = LayoutErrorBinding.bind(binding.error.root)
        val view =binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initRecyclerView()
        initViews()
    }

    private fun initPresenter() {
        presenter= NewsPresenter(this)
        presenter.getNews()
    }

    private fun initRecyclerView() {
        adapter = NewsAdapter{
            val newsUrl =it.url
            val customTab =CustomTabsIntent.Builder().build()
            customTab.launchUrl(requireContext(), Uri.parse(newsUrl))
        }
        binding.recyclerNews.adapter =adapter
        binding.recyclerNews.addOnScrollListener( object :OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                   if (!isLoading && !isFullyLoaded){
                       presenter.getNextPage()
                   }
                }

            }

        })
    }

    private fun initViews() {
        errorBinding.btnRetry.setOnClickListener {
            presenter.getNews()
        }
        binding.swipeRefreshLayout2.setOnRefreshListener {
            adapter.articles.clear()
            adapter.notifyDataSetChanged()
            presenter.refreshArticle()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun isLoading() {
        isLoading=true
        binding.swipeRefreshLayout2.isRefreshing = true
    }

    override fun articlesLoaded(articles: List<NewsResponce.Article>,totalArticles :Int?) {
        isLoading =false
        binding.swipeRefreshLayout2.isRefreshing = false
        adapter.articles.addAll(articles)
        adapter.notifyDataSetChanged()
        if (adapter.articles.size >= totalArticles?:0){
            isFullyLoaded=true
    }
    }

    override fun error(error: String, isFirstRequest: Boolean) {
        isLoading =false
        if(isFirstRequest){
            errorBinding.layoutError.visibility = VISIBLE
            errorBinding.txtError.text=error
        }else{
            Snackbar.make(binding.root,error,Snackbar.LENGTH_LONG).setAction("RETRY"){
                presenter.getNews()
            }.show()
        }
    }

}