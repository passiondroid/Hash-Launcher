package com.app.launcher.hash.ui.home;

import com.app.launcher.hash.ui.adapter.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by arif on 22/09/17.
 */

@Module
public class HomeActivityModule {

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(HomeActivity homeActivity) {
        return new ViewPagerAdapter(homeActivity.getSupportFragmentManager());
    }


}
