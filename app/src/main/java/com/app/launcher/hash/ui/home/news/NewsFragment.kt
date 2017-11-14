package com.app.launcher.hash.ui.home.news


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.launcher.hash.R
import com.app.launcher.hash.data.model.Article
import com.app.launcher.hash.data.model.NewsArticles
import com.app.launcher.hash.ui.adapter.NewsRVAdapter
import com.app.launcher.hash.ui.base.BaseFragment
import com.app.launcher.hash.ui.interfaces.OnListFragmentInteractionListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject


class NewsFragment : BaseFragment(), NewsInfoMvpView, OnListFragmentInteractionListener {

    @Inject
    lateinit var newsInfoPresenter : NewsInfoMvpPresenter<NewsInfoMvpView>

    private lateinit var adapter : NewsRVAdapter;

    //TODO: Add a repository pattern
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater!!.inflate(R.layout.fragment_news, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setUp(view: View?) {
        newsInfoPresenter.onAttach(this)
        swipeContainer.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                newsInfoPresenter?.getNewsArticles("techcrunch");
            }
        });

        //Get new news articles
        swipeContainer.post(Runnable {
            swipeContainer.setRefreshing(true)
            newsInfoPresenter?.getNewsArticles("techcrunch");
        })


        var newsArticles = NewsArticles()
        newsArticles.articles = ArrayList<Article>()
        newsArticles.source=""
        adapter = NewsRVAdapter(newsArticles, this)
        var linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = adapter;

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_orange_light,
                R.color.colorPrimary,
                android.R.color.holo_orange_light);

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment NewsFragment.
         */
        fun newInstance(): NewsFragment {
            val fragment = NewsFragment()
            return fragment
        }
    }

    override fun showNewsArtciles(newsArticles: NewsArticles) {
        adapter.setArtciles(newsArticles);
        swipeContainer.setRefreshing(false);
        adapter.notifyDataSetChanged()
    }

    override fun onListFragmentInteraction(view: View?, `object`: Any?, position: Int) {
        var url: String = (`object` as Article).url
        openInChromeTab(url)
    }

    private fun openInChromeTab(url: String) {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                val builder = CustomTabsIntent.Builder()
                builder.addDefaultShareMenuItem();
                val customTabsIntent = builder.build()
                val uri = Uri.parse(url)
                customTabsIntent.launchUrl(activity, uri)
            } catch (e: Exception) {
//                Crashlytics.logException(e)
                openInBrowser(url)
            }

        } else {
            openInBrowser(url)
        }
    }

    private fun openInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.`package` = "com.android.chrome"
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed so allow user to choose instead
            try {
                intent.`package` = null
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(activity, "No browser found to open the movie information", Toast.LENGTH_SHORT).show()
            }

        } catch (ex: Exception) {
            Toast.makeText(activity, "No browser found to open the movie information", Toast.LENGTH_SHORT).show()
        }

    }

}
