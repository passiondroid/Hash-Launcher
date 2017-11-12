package com.app.launcher.hash.rxBus;

/**
 * Created by arif on 16/10/17.
 */

public class MainFragmentVisibleEvent {

    private boolean isVisible;

    public MainFragmentVisibleEvent(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
