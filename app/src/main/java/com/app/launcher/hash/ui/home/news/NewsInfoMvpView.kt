package com.app.launcher.hash.ui.home.news

import com.app.launcher.hash.data.model.NewsArticles
import com.app.launcher.hash.ui.base.MvpView

/**
 * Created by arif on 07/11/17.
 */
interface NewsInfoMvpView : MvpView {

    fun showNewsArtciles( newsArticles: NewsArticles)
}