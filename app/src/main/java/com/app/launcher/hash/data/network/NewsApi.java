package com.app.launcher.hash.data.network;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.model.UnsplashPhoto;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by arif on 25/08/17.
 */

public interface NewsApi {

    @GET("/v1/articles")
    Observable<NewsArticles> getNewsArticles(@Query("source") String source, @Query("apiKey") String apiKey);
}
