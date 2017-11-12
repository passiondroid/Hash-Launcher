package com.app.launcher.hash.ui.base;


import android.util.Log;


import com.app.launcher.hash.data.network.AppApiHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private static final String TAG = "BasePresenter";
    private T mMvpView;

    private final AppApiHelper appApiHelper;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public BasePresenter(AppApiHelper appApiHelper, CompositeDisposable compositeDisposable) {
        this.appApiHelper = appApiHelper;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        if(compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public AppApiHelper getAppApiHelper() {
        return appApiHelper;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call MvpPresenter.attachView(MvpView) before" +
                    " requesting data to the MvpPresenter");
        }
    }

    @Override
    public void handleApiError(Throwable throwable) {
        Log.e(TAG, "handleApiError: ",throwable );
    }
}

