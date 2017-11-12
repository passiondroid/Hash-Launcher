package com.app.launcher.hash.rxBus;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class RxBus {

    private static PublishSubject<Object> publishSubject = PublishSubject.create();
    private static Disposable disposable;

    private RxBus() {
        // hidden constructor
    }

    public static Disposable subscribe(@NonNull Consumer<Object> action, Consumer<Throwable> throwableConsumer) {
        disposable = publishSubject.subscribe(action, throwableConsumer);
        return disposable;
    }

    public static void publish(@NonNull Object message) {
        publishSubject.onNext(message);
    }

    public static void unsubscribe(){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}

