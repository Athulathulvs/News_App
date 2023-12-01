package com.athulvs987.myapplication.presenter

import android.view.View
import com.athulvs987.myapplication.api.RetrofitInstance
import com.athulvs987.myapplication.model.newsModel.NewsResponce
import com.athulvs987.myapplication.utils.Constance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsPresenter(private val callback : NewsView) {

    private var  pageSize =25
    private var pageCount=1

    fun getNews(){
        callback.isLoading()
        RetrofitInstance.newsApi.getLatestNews(
            Constance.NEWS_COUNTRY_SOURCE,
            Constance.API_KEY,pageSize,pageCount)
            .enqueue(object : Callback<NewsResponce> {
                override fun onResponse(call: Call<NewsResponce>, response: Response<NewsResponce>) {
                    if (response.isSuccessful){
                        val articles =response.body()?.articles ?: emptyList()
                        callback.articlesLoaded(articles,response.body()?.totalResults)
                   } else{
                        callback.error("an unexpected error occurred..!",pageCount ==1)
                    }
                }

                override fun onFailure(call: Call<NewsResponce>, t: Throwable) {
                    t.printStackTrace()
                    callback.error("a network failure occurred..!",pageCount ==1)
               }

            })
    }

    fun refreshArticle() {
        pageCount = 1
        getNews()
    }

    fun getNextPage() {
        pageCount +=1
        getNews()
    }
}
interface NewsView{
    fun isLoading()
    fun articlesLoaded(articles :List<NewsResponce.Article>,totalArticles :Int?)
    fun error(error:String,isFirstRequest:Boolean)
}