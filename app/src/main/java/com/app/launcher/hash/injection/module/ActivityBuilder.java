package com.app.launcher.hash.injection.module;

import com.app.launcher.hash.ui.home.HomeActivity;
import com.app.launcher.hash.ui.home.HomeActivityModule;
import com.app.launcher.hash.ui.home.HomeFragmentDependencyProvider;
import com.app.launcher.hash.ui.onboarding.OnboardingActivity;
import com.app.launcher.hash.ui.onboarding.OnboardingActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by arif on 22/09/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = { HomeActivityModule.class, HomeFragmentDependencyProvider.class })
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = { OnboardingActivityModule.class})
    abstract OnboardingActivity bindOnboardingActivity();

}
