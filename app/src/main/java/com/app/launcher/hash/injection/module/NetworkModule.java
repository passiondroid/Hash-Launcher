package com.app.launcher.hash.injection.module;

import android.content.Context;

import com.app.launcher.hash.BuildConfig;
import com.app.launcher.hash.data.network.NewsApi;
import com.app.launcher.hash.data.network.UnsplashApi;
import com.app.launcher.hash.injection.ApplicationContext;
import com.app.launcher.hash.injection.HashRetrofit;
import com.app.launcher.hash.injection.NewsBaseUrl;
import com.app.launcher.hash.injection.UnsplashBaseUrl;
import com.app.launcher.hash.injection.UnsplashRetrofit;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arif on 22/09/17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    StethoInterceptor providesStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    Cache providesCache(@ApplicationContext Context context){
        return new Cache(context.getCacheDir(), 50 * 1024 * 1024);//50 MB cache
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, StethoInterceptor stethoInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache);
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(stethoInterceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    @HashRetrofit
    Retrofit providesHashRetrofit(@NewsBaseUrl String baseUrl, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @UnsplashRetrofit
    Retrofit providesUnsplashRetrofit(@UnsplashBaseUrl String baseUrl, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    NewsApi providesTmdbApi(@HashRetrofit Retrofit retrofit){
        return retrofit.create(NewsApi.class);
    }

    @Provides
    @Singleton
    UnsplashApi providesUnsplashApi(@UnsplashRetrofit Retrofit retrofit){
        return retrofit.create(UnsplashApi.class);
    }
}
