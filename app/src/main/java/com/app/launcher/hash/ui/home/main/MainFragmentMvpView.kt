package com.app.launcher.hash.ui.home.main

import com.app.launcher.hash.data.model.App
import com.app.launcher.hash.ui.base.MvpView
import io.realm.RealmResults

/**
 * Created by arif on 07/11/17.
 */
interface MainFragmentMvpView : MvpView {

    fun updateWallpaper(imageUrl: String)

    fun showAppsAndContacts(app: List<App>)
}