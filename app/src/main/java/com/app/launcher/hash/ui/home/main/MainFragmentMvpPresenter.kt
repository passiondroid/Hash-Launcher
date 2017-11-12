package com.app.launcher.hash.ui.home.main

import com.app.launcher.hash.ui.base.MvpPresenter
import com.app.launcher.hash.ui.home.news.NewsInfoMvpView
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent

/**
 * Created by arif on 07/11/17.
 */
interface MainFragmentMvpPresenter<V : MainFragmentMvpView> : MvpPresenter<V> {

    fun getUnsplashWallpaper()

    fun searchAppsAndContacts(observable: InitialValueObservable<TextViewAfterTextChangeEvent>)
}