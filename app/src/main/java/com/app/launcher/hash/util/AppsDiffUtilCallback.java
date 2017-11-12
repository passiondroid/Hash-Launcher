package com.app.launcher.hash.util;

import android.support.v7.util.DiffUtil;

import com.app.launcher.hash.data.model.App;

import java.util.List;

public class AppsDiffUtilCallback extends DiffUtil.Callback {

    private List<App> oldAppsList;
    private List<App> newAppsList;

    public AppsDiffUtilCallback(List<App> oldAppsList, List<App> newAppsList) {
        this.oldAppsList = oldAppsList;
        this.newAppsList = newAppsList;
    }

    @Override
    public int getOldListSize() {
        return oldAppsList.size();
    }

    @Override
    public int getNewListSize() {
        return newAppsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return !(oldAppsList.get(oldItemPosition) == null || newAppsList.get(newItemPosition) == null) && oldAppsList.get(oldItemPosition).getPckgName().equals(newAppsList.get(newItemPosition).getPckgName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return !(oldAppsList.get(oldItemPosition) == null || newAppsList.get(newItemPosition) == null) && oldAppsList.get(oldItemPosition).equals(newAppsList.get(newItemPosition));
    }
}
