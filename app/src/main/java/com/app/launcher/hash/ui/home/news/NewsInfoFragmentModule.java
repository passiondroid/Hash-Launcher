package com.app.launcher.hash.ui.home.news;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arif on 22/09/17.
 */

@Module
public class NewsInfoFragmentModule {

    @Provides
    NewsInfoMvpPresenter<NewsInfoMvpView> provideNewsInfoMvpPresenter(NewsInfoPresenter<NewsInfoMvpView> presenter) {
        return presenter;
    }
}
