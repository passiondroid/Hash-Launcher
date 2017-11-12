package com.app.launcher.hash.ui.home;

import com.app.launcher.hash.ui.home.main.MainFragment;
import com.app.launcher.hash.ui.home.main.MainFragmentModule;
import com.app.launcher.hash.ui.home.news.NewsFragment;
import com.app.launcher.hash.ui.home.news.NewsInfoFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by arif on 25/09/17.
 */

@Module
public abstract class HomeFragmentDependencyProvider {

    @ContributesAndroidInjector(modules = NewsInfoFragmentModule.class)
    abstract NewsFragment provideNewsFragmentFactory();

    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainFragment provideMainFragmentFactory();

}
