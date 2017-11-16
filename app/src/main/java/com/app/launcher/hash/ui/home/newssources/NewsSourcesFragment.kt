package com.app.launcher.hash.ui.home.newssources

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.launcher.hash.R
import com.app.launcher.hash.data.model.Article
import com.app.launcher.hash.data.model.NewsArticles
import com.app.launcher.hash.data.model.NewsSource
import com.app.launcher.hash.ui.adapter.NewsRVAdapter
import com.app.launcher.hash.ui.adapter.NewsSourcesRVAdapter
import com.app.launcher.hash.ui.base.BaseFragment
import com.app.launcher.hash.ui.home.news.NewsInfoMvpPresenter
import com.app.launcher.hash.ui.home.news.NewsInfoMvpView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.layout_news_source.*
import javax.inject.Inject

class NewsSourcesFragment : BaseFragment() {

//    @Inject
//    lateinit var newsInfoPresenter : NewsInfoMvpPresenter<NewsInfoMvpView>

//    @Inject
//    lateinit var sharedPreferences : SharedPreferences

    private lateinit var adapter : NewsSourcesRVAdapter;
    private lateinit var entAdapter : NewsSourcesRVAdapter;
    private lateinit var techAdapter : NewsSourcesRVAdapter;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater!!.inflate(R.layout.layout_news_source, container, false)
        return view
    }

    override fun onAttach(context: Context) {
//        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setUp(view: View?) {
//        newsInfoPresenter.onAttach(this)

        createGeneralNewsSources();

        adapter = NewsSourcesRVAdapter(createGeneralNewsSources())
        var linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        generalRV.layoutManager = linearLayoutManager
        ViewCompat.setNestedScrollingEnabled(generalRV, false)
        generalRV.adapter = adapter;

        createEntertainmentNewsSources();

        entAdapter = NewsSourcesRVAdapter(createEntertainmentNewsSources())
        var entLayoutManager = LinearLayoutManager(activity)
        entLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        entertainmentRV.layoutManager = entLayoutManager
        ViewCompat.setNestedScrollingEnabled(generalRV, false)
        entertainmentRV.adapter = entAdapter;

        createTechnologyNewsSources();

        techAdapter = NewsSourcesRVAdapter(createTechnologyNewsSources())
        var techLayoutManager = LinearLayoutManager(activity)
        techLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        technologyRV.layoutManager = techLayoutManager
        ViewCompat.setNestedScrollingEnabled(generalRV, false)
        technologyRV.adapter = techAdapter;
    }

    private fun createTechnologyNewsSources(): ArrayList<NewsSource> {
        var newsSource = NewsSource()
        newsSource.name="Ars Technica"
        newsSource.sourceName="ars-technica"

        var newsSource1 = NewsSource()
        newsSource1.name="Hacker News"
        newsSource1.sourceName="hacker-news"

        var newsSource2 = NewsSource()
        newsSource2.name="Mashable"
        newsSource2.sourceName="mashable"

        var newsSource3 = NewsSource()
        newsSource3.name="Recode"
        newsSource3.sourceName="recode"

        var newsSource4 = NewsSource()
        newsSource4.name="TechCrunch"
        newsSource4.sourceName="techcrunch"

        var newsSource5 = NewsSource()
        newsSource5.name="The Next Web"
        newsSource5.sourceName="the-next-web"

        var newsSource6 = NewsSource()
        newsSource6.name="The Verge"
        newsSource6.sourceName="the-verge"

        var newsSources = ArrayList<NewsSource>()
        newsSources.add(newsSource)
        newsSources.add(newsSource1)
        newsSources.add(newsSource2)
        newsSources.add(newsSource3)
        newsSources.add(newsSource4)
        newsSources.add(newsSource5)
        newsSources.add(newsSource6)

        return newsSources;
    }

    private fun createEntertainmentNewsSources(): ArrayList<NewsSource> {
        var newsSource = NewsSource()
        newsSource.name="Entertainment Weekly"
        newsSource.sourceName="entertainment-weekly"

        var newsSource1 = NewsSource()
        newsSource1.name="MTV News"
        newsSource1.sourceName="mtv-news"

        var newsSource2 = NewsSource()
        newsSource2.name="Buzzfeed"
        newsSource2.sourceName="buzzfeed"

        var newsSources = ArrayList<NewsSource>()
        newsSources.add(newsSource)
        newsSources.add(newsSource1)
        newsSources.add(newsSource2)

        return newsSources;
    }

    private fun createGeneralNewsSources(): ArrayList<NewsSource> {
        var newsSource = NewsSource()
        newsSource.name="Al Jazeera English"
        newsSource.sourceName="al-jazeera-english"

        var newsSource1 = NewsSource()
        newsSource1.name="Associated Press"
        newsSource1.sourceName="associated-press"

        var newsSource2 = NewsSource()
        newsSource2.name="BBC News"
        newsSource2.sourceName="bbc-news"

        var newsSource3 = NewsSource()
        newsSource3.name="Bloomberg"
        newsSource3.sourceName="bloomberg"

        var newsSource4 = NewsSource()
        newsSource4.name="CNN"
        newsSource4.sourceName="cnn"

        var newsSource5 = NewsSource()
        newsSource5.name="Google News"
        newsSource5.sourceName="google-news"

        var newsSource6 = NewsSource()
        newsSource6.name="Reuters"
        newsSource6.sourceName="reuters"

        var newsSource7 = NewsSource()
        newsSource7.name="Reddit All"
        newsSource7.sourceName="reddit-r-all"

        var newsSource8 = NewsSource()
        newsSource8.name="The Hindu"
        newsSource8.sourceName="the-hindu"

        var newsSource9 = NewsSource()
        newsSource9.name="The Huffington Post"
        newsSource9.sourceName="the-huffington-post"

        var newsSource10 = NewsSource()
        newsSource10.name="The New York Times"
        newsSource10.sourceName="the-new-york-times"

        var newsSource11 = NewsSource()
        newsSource11.name="The Washington Post"
        newsSource11.sourceName="the-washington-post"

        var newsSource12 = NewsSource()
        newsSource12.name="The Wall Street Journal"
        newsSource12.sourceName="the-wall-street-journal"

        var newsSource13 = NewsSource()
        newsSource13.name="Time"
        newsSource13.sourceName="time"

        var newsSource14 = NewsSource()
        newsSource14.name="USA Today"
        newsSource14.sourceName="usa-today"


        var newsSources = ArrayList<NewsSource>()
        newsSources.add(newsSource)
        newsSources.add(newsSource1)
        newsSources.add(newsSource2)
        newsSources.add(newsSource3)
        newsSources.add(newsSource4)
        newsSources.add(newsSource5)
        newsSources.add(newsSource6)
        newsSources.add(newsSource7)
        newsSources.add(newsSource8)
        newsSources.add(newsSource9)
        newsSources.add(newsSource10)
        newsSources.add(newsSource11)
        newsSources.add(newsSource12)
        newsSources.add(newsSource13)
        newsSources.add(newsSource14)

        return newsSources;

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment NewsFragment.
         */
        fun newInstance(): NewsSourcesFragment {
            val fragment = NewsSourcesFragment()
            return fragment
        }
    }



}