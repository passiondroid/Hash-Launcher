package com.app.launcher.hash.data.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by arif on 03/11/17.
 */

public class App extends RealmObject{

    @Index
    private String name; // app name
    private String pckgName; //package name
    private int iconResource; // app icon

    public String getPckgName() {
        return pckgName;
    }

    public void setPckgName(String pckgName) {
        this.pckgName = pckgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}


