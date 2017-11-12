package com.app.launcher.hash.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.launcher.hash.HashApp;
import com.app.launcher.hash.data.network.ApiHelper;
import com.app.launcher.hash.data.network.AppApiHelper;
import com.app.launcher.hash.injection.ApplicationContext;
import com.app.launcher.hash.injection.NewsBaseUrl;
import com.app.launcher.hash.injection.UnsplashBaseUrl;
import com.app.launcher.hash.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
public class ApplicationModule {

    @Provides
    @ApplicationContext
    Context provideContext(HashApp hashApp) {
        return hashApp.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @NewsBaseUrl
    String provideTmdbBaseUrl() {
        return Constants.NEWS_API_BASE_URL;
    }

    @Provides
    @UnsplashBaseUrl
    String provideUnsplashBaseUrl() {
        return Constants.UNSPLASH_API_BASE_URL;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

}
