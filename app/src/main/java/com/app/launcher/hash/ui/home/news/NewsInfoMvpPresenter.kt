package com.app.launcher.hash.ui.home.news

import com.app.launcher.hash.ui.base.MvpPresenter

/**
 * Created by arif on 07/11/17.
 */
interface NewsInfoMvpPresenter<V : NewsInfoMvpView> : MvpPresenter<V> {

    fun getNewsArticles(source : String)
}