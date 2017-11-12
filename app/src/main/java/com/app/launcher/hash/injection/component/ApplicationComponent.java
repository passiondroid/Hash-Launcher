package com.app.launcher.hash.injection.component;


import com.app.launcher.hash.HashApp;
import com.app.launcher.hash.injection.module.ActivityBuilder;
import com.app.launcher.hash.injection.module.ApplicationModule;
import com.app.launcher.hash.injection.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        NetworkModule.class,
        ApplicationModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(HashApp hashApp);

        ApplicationComponent build();

    }

    void inject(HashApp hashApp);

}
