package com.example.newsmvvm.ui.home

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsmvvm.R
import com.example.newsmvvm.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    fun getSources(){
        ApiManager.getApi().getSources(Constants.apiKey,lang = "en",country = "us")
            .enqueue(object :Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                   progressBarLiveData.value = false
                    if (response.isSuccessful){
                        sourcesLiveData.value = response.body()?.sources
                    }else{
                        messageLiveData.value = response.body()?.message!!
                    }

                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBarLiveData.value = false
                    messageLiveData.value = t.localizedMessage
                }
            })
    }

     fun getNews(sourceId: String?) {
        progressBarLiveData.value = true
         newsLiveData.value =null
        ApiManager.getApi().getNews(Constants.apiKey,sourceId?:"","")
                .enqueue(object :Callback<NewsResponse>{
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        progressBarLiveData.value = false
                        messageLiveData.value = t.localizedMessage
                    }

                    override fun onResponse(
                            call: Call<NewsResponse>,
                            response: Response<NewsResponse>
                    ) {
                        progressBarLiveData.value = false
                        if (response.isSuccessful){
                            newsLiveData.value = response.body()?.articles
                        }else{
                            messageLiveData.value = response.body()?.message!!
                        }

                    }

                })

    }
}