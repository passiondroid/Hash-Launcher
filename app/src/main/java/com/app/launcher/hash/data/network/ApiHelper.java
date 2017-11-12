package com.app.launcher.hash.data.network;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.model.UnsplashPhoto;

import io.reactivex.Observable;

/**
 * Created by vicky on 25/6/17.
 */

public interface ApiHelper {

    Observable<NewsArticles> getNewsArticles(String source);

    Observable<UnsplashPhoto> getUnsplashPhoto();

}
