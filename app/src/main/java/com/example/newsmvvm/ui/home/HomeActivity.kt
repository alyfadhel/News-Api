package com.example.newsmvvm.ui.home

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.adapter.NewsAdapter
import com.example.newsmvvm.api.ApiManager
import com.example.newsmvvm.api.ArticlesItem
import com.example.newsmvvm.api.NewsResponse
import com.example.newsmvvm.api.SourcesItem
import com.example.newsmvvm.ui.BaseActivity
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity(), TabLayout.OnTabSelectedListener{
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupViews()

        viewModel.getSources()
        observeLiveData()

    }
    private fun observeLiveData() {
        viewModel.messageLiveData.observe(this, Observer {message->
            showDialoge(message = message,posActionName = getString(R.string.ok),
            posAction = DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        })
        viewModel.progressBarLiveData.observe(this, Observer { show->
            if (show)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })
        viewModel.sourcesLiveData.observe(this, Observer {sourcesList->
            showSourcesInTabLayout(sourcesList)

        })
        viewModel.newsLiveData.observe(this, Observer { newsList->
            newsAdapter.changeData(newsList)

        })
    }
     private fun setupViews() {
        tabLayout = findViewById(R.id.tab_layout)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.RecyclerView)
        newsAdapter = NewsAdapter(null)
        recyclerView.adapter = newsAdapter

    }



     private fun showSourcesInTabLayout(sourcesList: List<SourcesItem?>?) {
        sourcesList?.forEach { item->
            val tab = tabLayout.newTab()
            tab.text = item?.name
            tab.tag = item
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.getTabAt(0)?.select()
    }
    override fun onTabReselected(tab: TabLayout.Tab?) {
        val item = tab?.tag as SourcesItem
        viewModel.getNews(item.id)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val item = tab?.tag as SourcesItem
        viewModel.getNews(item.id)

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }




}