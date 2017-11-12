package com.app.launcher.hash.data.network;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.model.UnsplashPhoto;
import com.app.launcher.hash.util.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by arif on 23/09/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private final NewsApi newsApi;

    private final UnsplashApi unsplashApi;

    @Inject
    public AppApiHelper(NewsApi newsApi, UnsplashApi unsplashApi) {
        this.newsApi = newsApi;
        this.unsplashApi = unsplashApi;
    }

    public Observable<NewsArticles> getNewsArticles(String source) {
        return newsApi.getNewsArticles(source, Constants.NEWS_API_KEY);
    }

    @Override
    public Observable<UnsplashPhoto> getUnsplashPhoto() {
        return unsplashApi.getUnsplashPhoto();
    }
}
