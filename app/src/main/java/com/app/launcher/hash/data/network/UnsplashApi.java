package com.app.launcher.hash.data.network;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.model.UnsplashPhoto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arif on 25/08/17.
 */

public interface UnsplashApi {

    @GET("/photos/random?client_id=2155e39e3e2e2208779cb08a2549c9c4682bd09b84a149ddf268f61024b03e50&orientation=portrait&featured")
    Observable<UnsplashPhoto> getUnsplashPhoto();
}
