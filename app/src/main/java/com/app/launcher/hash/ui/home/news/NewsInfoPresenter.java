package com.app.launcher.hash.ui.home.news;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.network.AppApiHelper;
import com.app.launcher.hash.ui.base.BasePresenter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by arif on 07/11/17.
 */

public class NewsInfoPresenter<V extends NewsInfoMvpView> extends BasePresenter<V> implements NewsInfoMvpPresenter<V>{

    @Inject
    public NewsInfoPresenter(AppApiHelper appApiHelper, CompositeDisposable compositeDisposable) {
        super(appApiHelper, compositeDisposable);
    }

    @Override
    public void getNewsArticles(@NotNull String source) {
        getCompositeDisposable().add(getAppApiHelper()
                .getNewsArticles(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsArticles>() {
                    @Override
                    public void accept(NewsArticles newsArticles) throws Exception {
                        getMvpView().showNewsArtciles(newsArticles);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handleApiError(throwable);
                        if (!isViewAttached()) {
                            return;
                        }
                    }
                })
            );
    }
}
