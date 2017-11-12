package com.app.launcher.hash.ui.home.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.app.launcher.hash.data.model.App;
import com.app.launcher.hash.injection.ApplicationContext;
import com.app.launcher.hash.ui.adapter.SearchRVAdapter;
import com.app.launcher.hash.ui.home.news.NewsInfoMvpPresenter;
import com.app.launcher.hash.ui.home.news.NewsInfoMvpView;
import com.app.launcher.hash.ui.home.news.NewsInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmResults;

/**
 * Created by arif on 22/09/17.
 */

@Module
public class MainFragmentModule {

    @Provides
    MainFragmentMvpPresenter<MainFragmentMvpView> provideMainFragmentMvpPresenter(MainFragmentPresenter<MainFragmentMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@ApplicationContext Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    SearchRVAdapter provideSearchAdapter() {
        return new SearchRVAdapter(new ArrayList<App>());
    }
}
