package com.app.launcher.hash.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class BaseFragment extends Fragment implements MvpView{

    private BaseActivity baseActivity;
//    private Dialog progressDialog;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        }
    }

    protected abstract void setUp(View view);

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = HashApp.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }


}
