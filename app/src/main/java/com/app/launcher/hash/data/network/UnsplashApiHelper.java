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
public class UnsplashApiHelper{

    private final UnsplashApi unsplashApi;

    @Inject
    public UnsplashApiHelper(UnsplashApi unsplashApi) {
        this.unsplashApi = unsplashApi;
    }

    public Observable<UnsplashPhoto> getUnsplashPhoto() {
        return unsplashApi.getUnsplashPhoto();
    }

}
