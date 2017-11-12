package com.app.launcher.hash.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by arif on 22/09/17.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UnsplashBaseUrl {
}
