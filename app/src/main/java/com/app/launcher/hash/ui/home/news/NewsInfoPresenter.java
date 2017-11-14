package com.app.launcher.hash.ui.home.news;

import com.app.launcher.hash.data.model.NewsArticles;
import com.app.launcher.hash.data.network.AppApiHelper;
import com.app.launcher.hash.ui.base.BasePresenter;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
        //TODO :: Ask sources from the user
        getCompositeDisposable()
                .add(Observable.zip(getAppApiHelper().getNewsArticles(source),
                        getAppApiHelper().getNewsArticles("al-jazeera-english")
                        , new BiFunction<NewsArticles, NewsArticles, NewsArticles>() {
                            @Override
                            public NewsArticles apply(NewsArticles newsArticles, NewsArticles newsArticles2) throws Exception {
                                newsArticles.getArticles().addAll(newsArticles2.getArticles());
//                                Collections.shuffle(newsArticles.getArticles());
                                return newsArticles;
                            }
                        })
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
