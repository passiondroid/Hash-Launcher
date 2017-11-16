package com.app.launcher.hash.ui.home.main;

import android.text.TextUtils;
import android.util.Log;

import com.app.launcher.hash.data.model.App;
import com.app.launcher.hash.data.model.UnsplashPhoto;
import com.app.launcher.hash.data.network.AppApiHelper;
import com.app.launcher.hash.ui.base.BasePresenter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by arif on 07/11/17.
 */

public class MainFragmentPresenter<V extends MainFragmentMvpView> extends BasePresenter<V> implements MainFragmentMvpPresenter<V>{

    private static final long QUERY_UPDATE_DELAY_MILLIS = 300;
    private static final String TAG = "MainFragmentPresenter";

    @Inject
    public MainFragmentPresenter(AppApiHelper appApiHelper, CompositeDisposable compositeDisposable) {
        super(appApiHelper, compositeDisposable);
    }

    @Override
    public void getUnsplashWallpaper() {
        getCompositeDisposable().add(getAppApiHelper()
                .getUnsplashPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UnsplashPhoto>() {
                    @Override
                    public void accept(UnsplashPhoto unsplashPhoto) throws Exception {
                        getMvpView().updateWallpaper(unsplashPhoto.getUrls().getRegular());
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

    @Override
    public void searchAppsAndContacts(@NotNull InitialValueObservable<TextViewAfterTextChangeEvent> observable) {
        getCompositeDisposable().add((observable)
                .debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .filter(new Predicate<TextViewAfterTextChangeEvent>() {
                    @Override
                    public boolean test(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        return !TextUtils.isEmpty(textViewAfterTextChangeEvent.editable());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        Realm realm = Realm.getDefaultInstance();
                        RealmResults<App> results = realm.where(App.class)
                                //TODO: Add a new column that contains app name with no space and a new column for app usage
                                //Apply search query on the basis of that
                                .contains("name", textViewAfterTextChangeEvent.editable().toString(), Case.INSENSITIVE)
                                .findAllAsync();
                        results.addChangeListener(new RealmChangeListener<RealmResults<App>>() {
                            @Override
                            public void onChange(RealmResults<App> apps) {
                                getMvpView().showAppsAndContacts(apps);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ",throwable );
                    }
                }));
    }
}
